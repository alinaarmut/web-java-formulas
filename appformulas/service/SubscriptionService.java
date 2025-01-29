package org.example.appformulas.service;

import org.example.appformulas.essence.Subscription;
import org.example.appformulas.essence.User;
import org.example.appformulas.essence.repository.SubscriptionRepository;
import org.example.appformulas.essence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;
public Map<String, String> activateSubscriptionForOneYear(String username) {
    try {
        Subscription subscription = getSubscriptionStatusByUsername(username);

        // Если подписка есть, обновляем её на активную
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1); // Устанавливаем дату окончания через год

        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setSubscriptionStatus("ACTIVE");

        subscriptionRepository.save(subscription);

        String subscriptionStatus = getSubscriptionStatus(subscription);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Подписка на год успешно активирована");
        response.put("subscriptionStatus", subscriptionStatus);  // Добавляем статус подписки

        return response;
    } catch (IllegalArgumentException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return errorResponse;
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Произошла ошибка при активации подписки");
        return errorResponse;
    }
}

    public String getSubscriptionStatus(Subscription subscription) {
        if (subscription == null || subscription.getEndDate() == null) {
            return "Subscription not active";
        }

        if (subscription.getEndDate().isAfter(LocalDate.now())) {
            return "ACTIVE";
        } else {
            return "INACTIVE";
        }
    }
    public Subscription getSubscriptionStatusByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Optional<Subscription> subscriptionOpt = subscriptionRepository.findByUserOrderByEndDateDesc(user.getId());
            return subscriptionOpt.orElseThrow(() -> new IllegalArgumentException("Подписка не найдена"));
        } else {
            throw new IllegalArgumentException("Пользователь с таким именем не найден");
        }
    }


}
