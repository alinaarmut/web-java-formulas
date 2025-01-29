package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "formulas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Formulas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_formulas", nullable = false)
    private String name;

    @Column(name = "type_formulas", nullable = false)
    private String type;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column(name = "formula", nullable = false)
    private String formula;
}
