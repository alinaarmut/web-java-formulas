package org.example.appformulas.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.appformulas.essence.*;
import org.example.appformulas.essence.repository.SubscriptionRepository;
import org.example.appformulas.essence.repository.TeacherRepository;
import org.example.appformulas.essence.repository.TokenRepository;
import org.example.appformulas.essence.repository.UserRepository;
import org.example.appformulas.essence.request.AuthRequest;
import org.example.appformulas.essence.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final SubscriptionRepository subscriptionRepository;
    private final TeacherRepository teacherRepository;

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getUsername());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach( t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    @Transactional
    public AuthResponse register(AuthRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Username taken.")
                    .status(HttpStatus.CONFLICT)
                    .statusCode(HttpStatus.CONFLICT.value())
                    .build();
        } else {

            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(TypeRole.valueOf(request.getRole()))
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .middleName(request.getMiddleName())
                    .build();
            var savedUser = repository.save(user);


            if (TypeRole.TEACHER.name().equals(request.getRole())) {
                String teacherCode = UUID.randomUUID().toString().substring(0, 6); // Генерация уникального кода учителя

                Teacher teacher = Teacher.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .middleName(request.getMiddleName())
                        .build();

                teacher.setTeacherCode(teacherCode);
                teacherRepository.save(teacher);


                savedUser.setTeacher(teacher);
                repository.save(savedUser);
            }

            // Если роль USER, привязываем пользователя к учителю по коду
            if (TypeRole.USER.name().equals(request.getRole()) && request.getTeacherCode() != null && !request.getTeacherCode().trim().isEmpty()) {
                Teacher teacher = teacherRepository.findByTeacherCode(request.getTeacherCode())
                        .orElseThrow(() -> new IllegalArgumentException("Teacher not found with code: " + request.getTeacherCode()));

                savedUser.setTeacher(teacher); // Привязываем учителя
                repository.save(savedUser); // Обновляем пользователя в БД
            }

            // Создание записи о подписке
            Subscription subscription = new Subscription();
            subscription.setUser(savedUser);
            subscription.setSubscriptionStatus("INACTIVE");
            subscription.setStartDate(null);
            subscription.setEndDate(null);
            subscriptionRepository.save(subscription);

            // Генерация токена
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);


            Map<String, Object> userData = new HashMap<>();
            userData.put("username", savedUser.getUsername());
            userData.put("email", savedUser.getEmail());
            userData.put("role", savedUser.getRole().name());
            userData.put("firstName", savedUser.getFirstName());
            userData.put("lastName", savedUser.getLastName());
            userData.put("middleName", savedUser.getMiddleName());

            if ("TEACHER".equals(savedUser.getRole().name()) && savedUser.getTeacher() != null) {
                userData.put("teacherCode", savedUser.getTeacher().getTeacherCode());
            }

            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("User registered.")
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .jwt(jwtToken)
                    .userData(userData)
                    .build();
        }
    }



    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        User user = repository.findByEmail(request.getEmail());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);


            Map<String, Object> userData = new HashMap<>();
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("role", user.getRole().name());
            userData.put("firstName", user.getFirstName());
            userData.put("lastName", user.getLastName());
            userData.put("middleName", user.getMiddleName());

            if ("USER".equals(user.getRole().name())) {
                String teacherCode = user.getTeacher().getTeacherCode();
                userData.put("teacherCode", teacherCode);
            }
            if ("TEACHER".equals(user.getRole().name())) {
                String teacherCode = user.getTeacher().getTeacherCode();
                userData.put("teacherCode", teacherCode);
            }


            return AuthResponse.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("User logged in.")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .jwt(jwtToken)
                    .userData(userData)
                    .build();
        }

        return AuthResponse.builder()
                .timeStamp(LocalDateTime.now())
                .message("Invalid username or password.")
                .status(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .build();
    }
}
