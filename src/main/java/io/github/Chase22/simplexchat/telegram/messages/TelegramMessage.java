package io.github.Chase22.simplexchat.telegram.messages;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramMessage {
    void execute(AbsSender sender, Long id) throws TelegramApiException;
}
