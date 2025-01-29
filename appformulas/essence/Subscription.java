package org.example.appformulas.essence;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "subscription",
        indexes = @Index(name = "_end_date", columnList = "end_date")
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "subscription_status", nullable = false)
    private String subscriptionStatus;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
