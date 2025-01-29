package org.example.appformulas.controller;

import org.example.appformulas.essence.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/app/chat/info")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
/*
    @MessageMapping("/sendMessage")
    public void sendMessage(Message message) {
        // Логика обработки и отправки сообщений
        System.out.println("Получено сообщение: " + message.getMessageText());

        // Отправляем сообщение всем подписанным пользователям на канал /topic/messages
        simpMessagingTemplate.convertAndSend("/topic/messages", message);
    }
*/

    @MessageMapping("/sendMessage")
    public void sendMessage( Message message) {
// Имя текущего пользователя (ученик)

//        // Проверяем, привязан ли ученик к указанному учителю
//        if (isStudentBoundToTeacher(username, message.getTeacherCode())) {
            // Отправляем сообщение в канал учителя
            simpMessagingTemplate.convertAndSend(
                    "/topic/messages/" + message.getTeacherCode(),
                    message
            );
//        } else {
//            throw new AccessDeniedException("Вы не можете отправить сообщение этому учителю");
//        }
    }



/*
    @MessageMapping("/sendMessage")
    @SendToUser("/queue/messages")
    public void sendMessage(Message message) {
        String receiverUsername = message.getReceiverUsername();
        System.out.println("Получено сообщение: " + message.getMessageText());
        System.out.println("Получатель сообщения: " + receiverUsername);

        if (receiverUsername != null && !receiverUsername.isEmpty()) {
            // Отправляем сообщение конкретному пользователю
            String destination = "/queue/messages";
            simpMessagingTemplate.convertAndSendToUser(receiverUsername, destination, message);
            System.out.println("Сообщение отправлено пользователю: " + receiverUsername);
            System.out.println("Канал: /user/" + receiverUsername + destination);
        } else {
            System.out.println("Ошибка: получатель не указан.");
        }
    }
*/
}
