package org.example.appformulas.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.appformulas.essence.Token;
import org.example.appformulas.essence.User;
import org.example.appformulas.essence.repository.TokenRepository;
import org.example.appformulas.essence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

//    @Transactional
//    public boolean deleteUserById(Long id) {
//        if (userRepository.existsById(id)) {
//            User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//            System.out.println("Before creating tokens set: " + user.getTokens());
//
//            Set<Token> tokens = new HashSet<>(user.getTokens());
//            System.out.println("After creating tokens set: " + tokens);
//
//            for (Token token : tokens) {
//                System.out.println("Before updating token: " + token);
//                token.setRevoked(true);
//                token.setExpired(true);
//                System.out.println("After updating token: " + token);
//            }
//
//            System.out.println("Before saving tokens");
//            tokenRepository.saveAll(tokens);
//            System.out.println("After saving tokens");
//
//            userRepository.deleteById(id);
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Transactional
    public boolean deleteUserById(Long id) {
        // Обновляем токены, отсоединяя их от пользователя
        tokenRepository.updateTokensForUser(id);

        // Удаляем пользователя
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

