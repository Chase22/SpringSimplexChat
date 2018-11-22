package org.chase.chat.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.annotation.TelegramBot;
import name.maratik.spring.telegram.annotation.TelegramCommand;
import name.maratik.spring.telegram.annotation.TelegramMessage;
import org.chase.chat.simplexchat.user.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.chase.chat.simplexchat.user.UsernameConverter.convertUsername;
import static org.springframework.util.StringUtils.isEmpty;

@TelegramBot
@Slf4j
public class TelegramChatController {

    private final UserService userService;

    public TelegramChatController(final UserService userService) {
        this.userService = userService;
    }

    @TelegramMessage
    public SendMessage defaultAction(Message message) {
        log.info(message.getText());
        return null;
    }

    @TelegramCommand(commands = "/start")
    public SendMessage startCommand(Message message) {
        return new SendMessage(message.getChatId(), "please set a Username by using /setUsername");
    }

    @TelegramCommand(commands = "/stop")
    public SendMessage stopCommand(Message message) {
        return userService.getUserByTelegramId(message.getFrom().getId()).map(userEntity -> {
            userEntity.setTelegramId(null);
            userService.save(userEntity);
            return new SendMessage(message.getChatId(), "You have been unregistered. You will no longer receive messages");
        }).orElse(
            new SendMessage(message.getChatId(), "You are not registered. Please set a Username using /setUsername")
        );
    }

    @TelegramCommand(commands = "/setUsername")
    public SendMessage setUserName(Message message, String argument) {
        if (isEmpty(argument)) {
            return new SendMessage(message.getChatId(), "No username provided");
        }

        return userService.getUserById(convertUsername(argument))
                .map(userEntity -> {
                    if (userEntity.getTelegramId() != null) {
                        return new SendMessage(message.getChatId(), "Username is already registered");
                    } else {
                        userEntity.setTelegramId(message.getChatId());
                        userService.save(userEntity);
                        return new SendMessage(message.getChatId(), "Username registered. You will now receive messages. To stop this, send /stop");
                    }
                })
                .orElse(new SendMessage(message.getChatId(), "Username not found. Please send a message with it in the web UI"));
    }

}
