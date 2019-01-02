package io.github.Chase22.simplexchat.telegram;

import io.github.Chase22.simplexchat.chat.ChatService;
import io.github.Chase22.simplexchat.message.MessageRouter;
import io.github.Chase22.simplexchat.user.UserService;
import io.github.Chase22.simplexchat.user.UsernameConverter;
import lombok.extern.slf4j.Slf4j;
import name.maratik.spring.telegram.annotation.TelegramBot;
import name.maratik.spring.telegram.annotation.TelegramCommand;
import name.maratik.spring.telegram.annotation.TelegramMessage;
import io.github.Chase22.simplexchat.message.MessageEntity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

@TelegramBot
@Slf4j
public class TelegramChatController {

    private final UserService userService;
    private final ChatService chatService;
    private final MessageRouter messageRouter;

    public TelegramChatController(final UserService userService, final ChatService chatService, final MessageRouter messageRouter) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageRouter = messageRouter;
    }

    @TelegramMessage
    public SendMessage defaultAction(Message message) {
        if (message.getText().length() > 1000) {
            return new SendMessage(message.getChatId(), "Messages may be at max 1000 characters long");
        }

        return userService.getUserByTelegramId(message.getFrom().getId()).map(userEntity -> {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setChat(chatService.getAllChats().get(0));
            messageEntity.setMessage(message.getText());
            messageEntity.setTimestamp(message.getDate()*1000L);
            messageEntity.setUser(userEntity);

            messageRouter.sendMessage(new io.github.Chase22.simplexchat.message.Message(messageEntity));

          return new SendMessage(message.getChatId(), "Message send");
        }).orElse(new SendMessage(message.getChatId(), "No Username set. Please set a Username by using /setUsername"));
    }

    @TelegramCommand(commands = "/start")
    public SendMessage startCommand(Message message) {
        return new SendMessage(message.getChatId(), "Please set a Username by using /setUsername");
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

        userService.getUserByTelegramId(message.getFrom().getId()).ifPresent(userEntity -> {
            userEntity.setTelegramId(null);
            userService.save(userEntity);
        });

        return userService.getUserById(UsernameConverter.convertUsername(argument))
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

    @TelegramCommand(commands = "/getUsername")
    public SendMessage getUsername(Long userId, Message message) {
        return userService.getUserByTelegramId(userId).map(
                userEntity -> new SendMessage()
                        .setText(format("Registered Username: %s", userEntity.getName()))
                        .setChatId(message.getChatId())
        ).orElse(new SendMessage().setText("No Username set").setChatId(message.getChatId()));
    }
}
