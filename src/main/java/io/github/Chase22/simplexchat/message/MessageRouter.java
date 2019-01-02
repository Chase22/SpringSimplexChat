package io.github.Chase22.simplexchat.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MessageRouter {

    private final List<MessageHandler> handlers;

    public MessageRouter(final List<MessageHandler> handlers) {
        handlers.forEach(messageHandler -> log.info("Found handler: {}", messageHandler.getClass().getSimpleName()));
        this.handlers = handlers;
    }

    public void sendMessage(Message message) {
        handlers.parallelStream().forEach(messageHandler -> messageHandler.accept(message));
    }
}
