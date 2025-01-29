package org.example.appformulas.essence.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserResponse extends Response {
    Map<String, ?> userData;

    @Builder(builderMethodName = "userResponseBuilder")
    public UserResponse(LocalDateTime timeStamp, int statusCode, HttpStatus status,
                        String message, String devMessage, Map<String, ?> userData) {
        super(timeStamp, statusCode, status, message, devMessage);
        this.userData = userData;
    }

    public static UserResponseBuilder builder() {
        return userResponseBuilder();
    }
}