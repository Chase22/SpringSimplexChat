package org.chase.chat.simplexchat.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseMessageHandler implements MessageHandler {
    private final MessageService messageService;

    public DatabaseMessageHandler(final MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void accept(final Message message) {
        messageService.save(message);
    }
}
