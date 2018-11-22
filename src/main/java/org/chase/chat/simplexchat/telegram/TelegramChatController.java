package org.chase.chat.simplexchat.telegram;

import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.annotation.TelegramBot;
import name.maratik.spring.telegram.annotation.TelegramCommand;
import name.maratik.spring.telegram.annotation.TelegramMessage;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

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

    @TelegramCommand(commands = "/setUsername")
    public SendMessage setUserName(Message message, String argument) {
        return userService.getUserById(argument)
                .map(userEntity -> {
                    if (userEntity.getTelegramId() != null) {
                        return new SendMessage(message.getChatId(), "Username is already registered");
                    } else {
                        userEntity.setTelegramId(message.getChatId());
                        userService.save(userEntity);
                        return new SendMessage(message.getChatId(), "Username registered. You will now receive messages");
                    }
                })
                .orElse(new SendMessage(message.getChatId(), "Username not found. Please send a message with it in the web UI"));
    }

}
