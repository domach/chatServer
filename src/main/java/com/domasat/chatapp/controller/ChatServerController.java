package com.domasat.chatapp.controller;

import com.domasat.chatapp.service.ChatServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatServerController {

    private final ChatServerService chatServerService;

    @PostMapping("/users")
    public void createUser(@RequestBody String username) {
        chatServerService.createUser(username);
    }

    @PostMapping("/groups")
    public void createUserGroup(@RequestBody String groupname) {
        chatServerService.createGroup(groupname);
    }

    @PostMapping("/groups/{groupname}/users")
    public void addUserToGroup(@PathVariable String groupname, @RequestBody String username) {
        chatServerService.addUserToGroup(username, groupname);
    }

    @DeleteMapping("/groups/{groupname}/users/{username}")
    public void removeUserFromGroup(@PathVariable String groupname, @PathVariable String username) {
        chatServerService.removeUserFromGroup(groupname, username);
    }

    @PostMapping("/groups/{groupname}/sendMessage")
    public void sendMessageToGroup(@PathVariable String groupname, @RequestBody String message) {
        chatServerService.sendMessageToGroup(groupname, message);
    }

    @PostMapping("/users/{username}/sendMessage")
    public void sendMessageToUser(@PathVariable String username, @RequestBody String message) {
        chatServerService.sendMessageToUser(username, message);
    }

}
