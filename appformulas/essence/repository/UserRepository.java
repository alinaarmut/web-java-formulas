package org.example.appformulas.essence.repository;

import org.example.appformulas.essence.Teacher;
import org.example.appformulas.essence.TypeRole;
import org.example.appformulas.essence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername (String username);
    List<User> findByRole(TypeRole role);
    User findByEmail(String email);
    List<User> findAllByTeacher(Teacher teacher);




}