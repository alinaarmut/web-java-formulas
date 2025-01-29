package org.example.appformulas.essence.response;


import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(callSuper = true)

public class ProgressResponse extends Response {

    Map<String, ?> userData;
    private String jwt;
    @Builder(builderMethodName = "progressResponseBuilder")
    public ProgressResponse(LocalDateTime timeStamp, int statusCode, HttpStatus status,
                        String message, String devMessage, Map<String, ?> userData, String jwt) {
        super(timeStamp, statusCode, status, message, devMessage);
        this.jwt = jwt;
        this.userData = userData;
    }

    public static ProgressResponse.ProgressResponseBuilder builder() {
        return progressResponseBuilder();
    }
}
