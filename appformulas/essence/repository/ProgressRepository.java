package org.example.appformulas.essence.repository;


import org.example.appformulas.essence.Progress;
import org.example.appformulas.essence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findTopByUserOrderByTimestampDesc(User user);

}
