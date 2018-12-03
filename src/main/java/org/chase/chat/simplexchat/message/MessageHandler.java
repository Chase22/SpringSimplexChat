package org.chase.chat.simplexchat.message;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public interface MessageHandler extends Consumer<Message> {
    @Override
    @Async
    void accept(Message message);
}
