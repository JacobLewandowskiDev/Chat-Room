package com.jakubchatapp.websocketdemo.controller;

import com.jakubchatapp.websocketdemo.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    // Listen to the websockets session for a connect event and log in the console a message about the event
    @EventListener
    public void webSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection: " + event);
    }

    // Listen to the websocket session for a disconnect event and log to the console a appropriate message
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User: " + username + " has disconnected from the chat.");

            // Create an instance of ChatMessage and set its parameters (sender and messageType)
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            // Convert the given Object to serialized form, possibly using a MessageConverter, wrap it as a message and send it to the given destination.
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}