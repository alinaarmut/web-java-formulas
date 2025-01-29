package org.example.appformulas.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.appformulas.essence.Progress;
import org.example.appformulas.essence.User;
import org.example.appformulas.essence.repository.ProgressRepository;
import org.example.appformulas.essence.repository.UserRepository;
import org.example.appformulas.essence.request.ProgressRequest;
import org.example.appformulas.essence.response.ProgressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;


    @Transactional
    public ProgressResponse saveProgress(ProgressRequest progressRequest) {
        String username = progressRequest.getUsername();
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null) {
            throw new UsernameNotFoundException("Пользователь с именем " + username + " не найден");
        }
        Long userId = currentUser.getId();

        Progress progress = Progress.builder()
                .user(currentUser)
                .result(progressRequest.getProgressResult())
                .build();

        Progress savedProgress = progressRepository.save(progress);

        Map<String, Object> userData = new HashMap<>();
        userData.put("username", currentUser.getUsername());
        userData.put("userId", userId);
        userData.put("progress", savedProgress);

        return ProgressResponse.builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Прогресс успешно сохранен.")
                .devMessage("Данные прогресса сохранены в базе.")
                .userData(userData)
                .build();
    }

    public Double getProgressResultByUsername(String username) {
        // Проверка наличия пользователя
        if (!userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с именем " + username + " не найден");
        }

        // Получение пользователя
        User user = userRepository.findByUsername(username);

        // Поиск прогресса, создание нового, если не найден
        Progress progress = progressRepository.findTopByUserOrderByTimestampDesc(user)
                .orElseGet(() -> {
                    // Создаем прогресс с начальным значением 0
                    Progress newProgress = Progress.builder()
                            .user(user)
                            .result(0.0)
                            .build();
                    return progressRepository.save(newProgress);
                });

        return progress.getResult();
    }
}