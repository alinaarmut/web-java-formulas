package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Exercises {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_exercises", nullable = false)
    private String name;

    @Column(name = "description_exercises", nullable = false)
    private String description;

    @Column(name = "text_exercises", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
