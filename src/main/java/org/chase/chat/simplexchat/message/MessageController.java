package org.chase.chat.simplexchat.message;

import org.chase.chat.simplexchat.misc.RestApiController;
import org.chase.chat.simplexchat.telegram.TelegramAsyncMessageSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestApiController("api/message")
public class MessageController {

    private final MessageService messageService;
    private final TelegramAsyncMessageSender asyncMessageSender;

    public MessageController(final MessageService messageService, final TelegramAsyncMessageSender asyncMessageSender) {
        this.messageService = messageService;
        this.asyncMessageSender = asyncMessageSender;
    }

    @GetMapping("/{id}")
    public MessageEntity getMessage(@PathVariable(name = "id") Long id) {
        return messageService.getById(id);
    }

    @PostMapping(value = "/send", consumes = APPLICATION_JSON_VALUE)
    public void sendMessage(@RequestBody SendMessageRequestObject requestObject) {
        final MessageEntity entity = messageService.RequestObjectToEntity(requestObject);
        asyncMessageSender.sendMessages(entity);
        messageService.save(entity);
    }
}
