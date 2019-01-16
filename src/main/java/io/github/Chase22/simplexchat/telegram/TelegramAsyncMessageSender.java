package io.github.Chase22.simplexchat.telegram;

import io.github.Chase22.simplexchat.message.Message;
import io.github.Chase22.simplexchat.telegram.messages.TelegramMessage;
import io.github.Chase22.simplexchat.telegram.messages.TextMessage;
import io.github.Chase22.simplexchat.user.UserService;
import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.TelegramBotService;
import io.github.Chase22.simplexchat.message.MessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.String.format;

@Component
@Slf4j
public class TelegramAsyncMessageSender implements MessageHandler {

    private final UserService userService;
    private final AbsSender bot;
    private final TelegramMessageHandler messageHandler;

    public TelegramAsyncMessageSender(final UserService userService, final TelegramBotService telegramBotService, final TelegramMessageHandler messageHandler) {
        this.userService = userService;
        this.bot = telegramBotService.getClient();
        this.messageHandler = messageHandler;
    }

    @Override
    public void accept(final Message message) {
        TelegramMessage telegramMessage = messageHandler.handleMessage(message);

        userService.getAllUser().stream()
                .filter(userEntity -> userEntity.getTelegramId() != null)
                .filter(userEntity -> !userEntity.getName().equals(message.getUsername())).parallel()
                .forEach(userEntity -> {
                    try {
                        telegramMessage.execute(bot, userEntity.getTelegramId());
                    } catch (TelegramApiException e) {
                        log.error("Could not send message", e);
                    }
                });
    }
}
