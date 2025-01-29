package org.example.appformulas.controller;

import org.example.appformulas.essence.User;
import org.example.appformulas.essence.response.UserResponse;
import org.example.appformulas.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/app/teacher")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {
    @Autowired
    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/students")
    public ResponseEntity<UserResponse> getStudentsByTeacherCode(@RequestParam("code") String teacherCode) {
        try {
            List<User> students = teacherService.getStudentsByTeacherCode(teacherCode);
            if (students.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new UserResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND, "No students found for the provided teacher code", "", null));
            }
            Map<String, Object> studentData = Map.of("students", students);
            return ResponseEntity.ok(new UserResponse(LocalDateTime.now(), HttpStatus.OK.value(),
                    HttpStatus.OK, "Students loaded successfully", "", studentData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UserResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR, "Error loading students", e.getMessage(), null));
        }
    }


}
