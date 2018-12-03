package org.chase.chat.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.TelegramBotService;
import org.chase.chat.simplexchat.message.Message;
import org.chase.chat.simplexchat.message.MessageHandler;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.lang.String.format;

@Component
@Slf4j
public class TelegramAsyncMessageSender implements MessageHandler {

    private final UserService userService;
    private final AbsSender bot;

    public TelegramAsyncMessageSender(final UserService userService, final TelegramBotService telegramBotService) {
        this.userService = userService;
        this.bot = telegramBotService.getClient();
    }

    private String formatMessage(Message message) {
        return format("%s %n-----%n_%s_", message.getMessage(), message.getUsername());
    }

    @Override
    public void accept(final Message message) {
        StreamSupport.stream(userService.getAllUser().spliterator(), false)
                .filter(userEntity -> userEntity.getTelegramId() != null)
                .filter(userEntity -> !userEntity.getName().equals(message.getUsername())).parallel()
                .forEach(userEntity -> {
                    try {
                        bot.execute(new SendMessage(userEntity.getTelegramId(), formatMessage(message)).setParseMode(ParseMode.MARKDOWN));
                    } catch (TelegramApiException e) {
                        log.error("Could not send message", e);
                    }
                });
    }
}
