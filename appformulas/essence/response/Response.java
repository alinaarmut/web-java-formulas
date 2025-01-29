package org.example.appformulas.essence.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    @JsonIgnore
    private String devMessage;
//    @JsonIgnore
//    private String username;
//    private String role;
}
