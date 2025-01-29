package org.example.appformulas.essence.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String teacherCode;
}
