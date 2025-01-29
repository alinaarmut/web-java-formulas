package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "code", unique = true, nullable = false)
    private String  teacherCode;

    @OneToMany(fetch = FetchType.EAGER)
    private List<User> users;

}


