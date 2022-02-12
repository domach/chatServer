package com.domasat.chatapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServerService {

    private final RabbitAdmin rabbitAdmin;

    public void createUser(String username) {
        String result = rabbitAdmin.declareQueue(new Queue(username));
        log.info("Created new queue: " + result);
    }

    public void createGroup(String groupname) {
        rabbitAdmin.declareExchange(new FanoutExchange(groupname));
        log.info("Created new exchange: " + groupname);
    }

    public void addUserToGroup(String username, String groupname) {
        rabbitAdmin.declareBinding(new Binding(username, Binding.DestinationType.QUEUE, groupname, "", null));
        log.info("Created new binding between queue: " + username + " and exchange: " + groupname);
    }

    public void removeUserFromGroup(String groupname, String username) {
        rabbitAdmin.removeBinding(new Binding(username, Binding.DestinationType.QUEUE, groupname, "", null));
        log.info("Removed a binding between queue: " + username + " and exchange: " + groupname);
    }

    public void sendMessageToGroup(String groupname, String message) {
        rabbitAdmin.getRabbitTemplate().convertAndSend(groupname, "", message);
        log.info("A message was sent to exchange: " + groupname);
    }

    public void sendMessageToUser(String username, String message) {
        rabbitAdmin.getRabbitTemplate().convertAndSend("", username, message);
        log.info("A message was sent to user: " + username);
    }
}
