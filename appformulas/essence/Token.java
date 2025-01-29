package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Table(name="app_tokens", schema="s367826")
public class Token {
    @Id
    @GeneratedValue
    @SequenceGenerator(name = "tokens_id_seq_generator", sequenceName = "tokens_id_generator", allocationSize = 1)
    private Long id;

    private String token;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
