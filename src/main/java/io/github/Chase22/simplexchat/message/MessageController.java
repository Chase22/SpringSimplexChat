package io.github.Chase22.simplexchat.message;

import io.github.Chase22.simplexchat.misc.RestApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApiController("api/message")
public class MessageController {

    private final MessageRouter messageRouter;
    private final MessageService messageService;

    public MessageController(final MessageRouter messageRouter, final MessageService messageService) {
        this.messageRouter = messageRouter;
        this.messageService = messageService;
    }

    @GetMapping("/{id}")
    public MessageRVO getMessage(@PathVariable(name = "id") Long id) {
        return messageService.getById(id);
    }

    @PostMapping(value = "/send", consumes = APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody Message message) {
        messageRouter.sendMessage(message);
    }
}
