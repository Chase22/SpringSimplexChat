package org.chase.chat.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.TelegramBotService;
import org.chase.chat.simplexchat.message.MessageEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.StreamSupport;

import static java.lang.String.format;

@Component
@Slf4j
public class TelegramAsyncMessageSender {

    private final UserService userService;
    private final AbsSender bot;

    public TelegramAsyncMessageSender(final UserService userService, final TelegramBotService telegramBotService) {
        this.userService = userService;
        this.bot = telegramBotService.getClient();
    }

    @Async
    public void sendMessages(MessageEntity messageEntity) {
        StreamSupport.stream(userService.getAllUser().spliterator(), false)
                .filter(userEntity -> userEntity.getTelegramId() != null)
                .filter(userEntity -> !userEntity.getName().equals(messageEntity.getUser().getName())).parallel()
                .forEach(userEntity -> {
                    try {
                        bot.execute(new SendMessage(userEntity.getTelegramId(), formatMessage(messageEntity)).setParseMode(ParseMode.MARKDOWN));
                    } catch (TelegramApiException e) {
                        log.error("Could not send message", e);
                    }
                });
    }

    private String formatMessage(MessageEntity messageEntity) {
        return format("%s %n-----%n_%s_", messageEntity.getMessage(), messageEntity.getUser().getName());
    }

}
