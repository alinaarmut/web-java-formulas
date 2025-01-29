package org.example.appformulas.essence.repository;


import org.example.appformulas.essence.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByTeacherCode(String teacherCode);
}
