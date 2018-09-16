package org.chase.chat.simplexchat.chat;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.misc.RestApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RestApiController("api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(final ChatService chatService) {
        this.chatService = requireNonNull(chatService, "chatService");
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
    public ResponseEntity getChat(@PathVariable("id") final String id) {
        Optional<ChatEntity> chat = chatService.getChatById(id);
        if (chat.isPresent()) {
            return ResponseEntity.ok(chat.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    @PutMapping("")
    public ResponseEntity<ChatEntity> insertOrUpdateChat(@RequestBody final ChatEntity chatEntity) {
        chatService.insertOrUpdate(chatEntity);
        return ResponseEntity.ok(chatEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") final String id) {
        chatService.deleteChatById(id);
    }

}
