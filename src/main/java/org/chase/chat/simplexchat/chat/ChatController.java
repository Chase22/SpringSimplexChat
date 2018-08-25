package org.chase.chat.simplexchat.chat;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.misc.RestApiController;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestApiController("chat")
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(final ChatService chatService, UserService userService) {
        this.chatService = requireNonNull(chatService, "chatService");
        this.userService = requireNonNull(userService, "userService");
    }

    @GetMapping("test")
    @ResponseStatus(HttpStatus.OK)
    public void replyOK() {
    }

    @GetMapping("")
    public ResponseEntity<List<Chat>> getAllChats() {
        return new ResponseEntity<>(
                IteratorUtils.toList(
                        chatService.getAllChats()
                                .iterator())
                        .stream()
                        .map(ChatEntity::toBusinessObject)
                        .collect(Collectors.toList()
                        ), HttpStatus.OK);
    }

    @GetMapping("/byUserId/{id}")
    public ResponseEntity<List<Chat>> getAllChatsByUserID(@PathVariable("id") final String userID) {
        return new ResponseEntity<>(
                userService.getUserById(userID)
                        .getChats()
                        .stream()
                        .map(ChatEntity::toBusinessObject)
                        .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable("id") final String id) {
        return new ResponseEntity<>(chatService.getChatById(id).toBusinessObject(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Chat> insertChat(@RequestBody final Chat chatEntity) {
        chatService.insertOrUpdate(chatEntity);
        return new ResponseEntity<>(chatEntity, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<ChatEntity> updateChat(@RequestBody final ChatEntity chatEntity) {
        chatService.insertOrUpdate(chatEntity);
        return new ResponseEntity<>(chatEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") final String id) {
        chatService.deleteChatById(id);
    }

}
