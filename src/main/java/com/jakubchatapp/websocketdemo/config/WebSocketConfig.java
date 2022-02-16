package com.jakubchatapp.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//The @EnableWebSocketMessageBroker is used to enable our WebSocket server.
// We implement WebSocketMessageBrokerConfigurer interface and provide
// implementation for some of its methods to configure the websocket connection.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // In the first method, we register a websocket endpoint that the clients
    // will use to connect to our websocket server

    // SockJS is used to enable fallback options for browsers that don’t support websocket.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    // In the second method, we’re configuring a message broker that will be used to route
    // messages from one client to another.
    //
    // The first line defines that the messages whose destination starts with “/app” should
    // be routed to message-handling methods (we’ll define these methods shortly).
    //
    // And, the second line defines that the messages whose destination starts with “/topic”
    // should be routed to the message broker. Message broker broadcasts messages to all
    // the connected clients who are subscribed to a particular topic.

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }

}
