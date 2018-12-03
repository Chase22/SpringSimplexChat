package org.chase.chat.simplexchat.chat;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.message.MessageEntity;
import org.chase.chat.simplexchat.message.MessageFormatter;
import org.chase.chat.simplexchat.message.MessageRVO;
import org.chase.chat.simplexchat.misc.RestApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestApiController("api/chat")
public class ChatController {
    private final ChatService chatService;
    private final MessageFormatter messageFormatter;

    public ChatController(final ChatService chatService, final MessageFormatter messageFormatter) {
        this.chatService = chatService;
        this.messageFormatter = messageFormatter;
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
        return chat.<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/message")
    public List<MessageRVO> getMessages(@PathVariable("id") String id,
                                        @RequestParam(value = "offset", defaultValue = "-1") Integer offset,
                                        @RequestParam(value = "limit", defaultValue = "100") Integer limit) {

        return chatService.getChatById(id).map(ChatEntity::getMessages)
                .orElseThrow(ChatNotFoundException::new)
                .stream()
                .filter(messageEntity -> messageEntity.getId() > offset)
                .sorted(Comparator.comparingLong(MessageEntity::getTimestamp))
                .limit(limit)
                .peek(messageEntity -> messageEntity.setMessage(messageFormatter.format(messageEntity.getMessage())))
                .map(MessageEntity::toRVO)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    @PutMapping("")
    public ResponseEntity<ChatEntity> insertOrUpdateChat(@RequestBody final ChatEntity chatEntity) {
        chatService.save(chatEntity);
        return ResponseEntity.ok(chatEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteChat(@PathVariable("id") final String id) {
        chatService.deleteChatById(id);
    }

}
