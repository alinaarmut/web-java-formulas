package org.example.appformulas.service;

import org.example.appformulas.essence.Teacher;
import org.example.appformulas.essence.User;
import org.example.appformulas.essence.repository.TeacherRepository;
import org.example.appformulas.essence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private final TeacherRepository teacherRepository;
    private final UserRepository studentRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, UserRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<User> getStudentsByTeacherCode(String teacherCode) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherCode)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        return studentRepository.findAllByTeacher(teacher);
    }
}
