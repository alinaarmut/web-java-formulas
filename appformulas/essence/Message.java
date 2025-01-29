package org.example.appformulas.essence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    private String receiverUsername;
    private String messageText;
    private String teacherCode;
}
