package com.jacobferrell.chess.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.jacobferrell.chess.model.GameModel;
import com.jacobferrell.chess.model.GameRepository;
import com.jacobferrell.chess.model.Notification;
import com.jacobferrell.chess.model.NotificationRepository;

@Controller
public class NotificationController {

    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private GameRepository gameRepository;

    @MessageMapping("/notify")
    public void processNotification(@Payload Notification notification) {
        Optional<GameModel> game = gameRepository.findById(notification.getGame().getId());
        if (!game.isPresent()) {
            return;
        }
        Long gameId =  game.get().getId();

        notificationRepository.save(notification);

        //messagingTemplate.convertAndSendToUser("/queue/moves", notification);
    }

    
}
