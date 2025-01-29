package org.example.appformulas.controller;

import org.example.appformulas.essence.request.ProgressRequest;
import org.example.appformulas.essence.response.ProgressResponse;
import org.example.appformulas.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/app/exerciseOne")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @PostMapping("/progress")
    public ResponseEntity<ProgressResponse> saveProgress(@RequestBody ProgressRequest request) {
        System.out.println(request);
        return ResponseEntity.ok(progressService.saveProgress(request));
    }

    @GetMapping("/progress/{username}")
    public ResponseEntity<Double> getProgress(@PathVariable String username) {
        Double result = progressService.getProgressResultByUsername(username);
        return ResponseEntity.ok(result);
    }

}

