package org.example.appformulas.controller;


import org.example.appformulas.essence.User;
import org.example.appformulas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/app/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    @Autowired
    private UserService userService;


    public AdminController(UserService userService) {}
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("Attempting to delete user with ID: {}"+ id);
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            System.out.println("User with ID: {} successfully deleted."+ id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
