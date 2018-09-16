package org.chase.chat.simplexchat.message;

import org.chase.chat.simplexchat.misc.RestApiController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static java.util.Objects.requireNonNull;

@RestApiController("api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(final MessageService messageService) {
        this.messageService = requireNonNull(messageService, "messageService");
    }

    @GetMapping("/{id}")
    public MessageEntity sendMessage(@PathVariable(name = "id") Long id) {
        return messageService.getById(id);
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody SendMessageRequestObject requestObject) {
        MessageEntity entity = messageService.RequestObjectToEntity(requestObject);
        messageService.insertOrUpdateMessage(entity);
    }
}
