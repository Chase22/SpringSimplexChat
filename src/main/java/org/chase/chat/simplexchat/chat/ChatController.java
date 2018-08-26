package org.chase.chat.simplexchat.chat;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.misc.RestApiController;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ChatEntity>> getAllChats() {
        return new ResponseEntity<>(IteratorUtils.toList(chatService.getAllChats().iterator()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatEntity> getChat(@PathVariable("id") final String id) {
        return new ResponseEntity<>(chatService.getChatById(id), HttpStatus.OK);
    }

    @PostMapping("")
    @PutMapping("")
    public ResponseEntity<ChatEntity> insertOrUpdateChat(@RequestBody final ChatEntity chatEntity) {
        chatService.insertOrUpdate(chatEntity);
        return new ResponseEntity<>(chatEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") final String id) {
        chatService.deleteChatById(id);
    }

}
