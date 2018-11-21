package org.chase.chat.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.annotation.TelegramBot;
import name.maratik.spring.telegram.annotation.TelegramMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

@TelegramBot
@Slf4j

public class TelegramChatController {

    @TelegramMessage
    public SendMessage defaultAction(long userId, User user, String message) {
        log.info(message);
        return new SendMessage()
                .setChatId(userId)
                .setText(String.format("Hello %s, you've send me %s", user.getFirstName(), message));
    }

}
