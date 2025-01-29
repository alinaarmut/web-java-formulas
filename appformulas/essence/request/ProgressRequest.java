package org.example.appformulas.essence.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressRequest {
    private String username;
    private Double progressResult;
}
