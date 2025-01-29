package org.example.appformulas.controller;

import org.example.appformulas.essence.Subscription;
import org.example.appformulas.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/app/subscription")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/status/{username}")
    public ResponseEntity<?> getSubscriptionStatus(@PathVariable String username) {
        try {
            Subscription subscription = subscriptionService.getSubscriptionStatusByUsername(username);

            return ResponseEntity.ok(subscription);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/buy/{username}")
    public ResponseEntity<?> setSubscriptionForOneYear(@PathVariable String username) {
        // Вызов сервиса для активации подписки
        Map<String, String> response = subscriptionService.activateSubscriptionForOneYear(username);
        if (response.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    }