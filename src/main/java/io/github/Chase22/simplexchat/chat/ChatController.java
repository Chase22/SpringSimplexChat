package io.github.Chase22.simplexchat.chat;

import io.github.Chase22.simplexchat.message.MessageEntity;
import io.github.Chase22.simplexchat.message.MessageRVO;
import io.github.Chase22.simplexchat.misc.RestApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;
import java.util.stream.Collector;

@RestApiController("api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(final ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("test")
    @ResponseStatus(HttpStatus.OK)
    public void replyOK() {
    }

    @GetMapping("")
    public ResponseEntity<List<ChatEntity>> getAllChats() {
        return new ResponseEntity<>(chatService.getAllChats(), HttpStatus.OK);
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
                .filter(messageEntity -> !StringUtils.isEmpty(messageEntity.getMessage()))
                .sorted(Comparator.comparingLong(MessageEntity::getTimestamp))
                .peek(messageEntity -> messageEntity.setMessage(HtmlUtils.htmlEscape(messageEntity.getMessage())))
                .map(MessageEntity::toRVO)
                .collect(lastN(limit));
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

    public static <T> Collector<T, ?, List<T>> lastN(int n) {
        return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
            if(acc.size() == n)
                acc.pollFirst();
            acc.add(t);
        }, (acc1, acc2) -> {
            while(acc2.size() < n && !acc1.isEmpty()) {
                acc2.addFirst(acc1.pollLast());
            }
            return acc2;
        }, ArrayList::new);
    }

}
