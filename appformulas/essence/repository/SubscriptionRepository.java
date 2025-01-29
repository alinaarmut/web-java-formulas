package org.example.appformulas.essence.repository;

import org.example.appformulas.essence.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(value = "SELECT * FROM subscription WHERE user_id = :userId ORDER BY end_date DESC LIMIT 1", nativeQuery = true)
    Optional<Subscription> findByUserOrderByEndDateDesc(@Param("userId") Long userId);


}
