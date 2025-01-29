package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "users_progress",
        indexes = @Index(name = "_user_progress", columnList = "user_id")
)

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double result;

    @Column(name = "result_type")
    private String resultType;

//    @Column(nullable = false)
    private String access;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now(); // Устанавливаем timestamp при создании
    }

}