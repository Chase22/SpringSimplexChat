package org.chase.chat.simplexchat.message;

import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.telegram.TelegramAsyncMessageSender;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;
import static org.chase.chat.simplexchat.user.UsernameConverter.convertUsername;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;
    private final TelegramAsyncMessageSender asyncMessageSender;


    public MessageService(MessageRepository messageRepository, ChatService chatService, UserService userService, final TelegramAsyncMessageSender asyncMessageSender) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.userService = userService;
        this.asyncMessageSender = asyncMessageSender;
    }

    public MessageEntity getById(long id) {
        return messageRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public void sendMessage(MessageEntity entity) {
        asyncMessageSender.sendMessages(entity);
        save(entity);
    }

    public void save(MessageEntity entity) {
        messageRepository.save(entity);
    }

    public void deleteMessage(MessageEntity entity) {
        messageRepository.delete(entity);
    }

    public void deleteMessage(long id) {
        messageRepository.deleteById(id);
    }

    public MessageEntity RequestObjectToEntity(SendMessageRequestObject requestObject) {
        MessageEntity entity = new MessageEntity();
        entity.setChat(chatService.getChatById(requestObject.getChatid()).orElseThrow(NullPointerException::new));
        entity.setMessage(requestObject.getMessage());
        entity.setTimestamp(requestObject.getTimestamp());
        entity.setUser(userService.getUserById(convertUsername(requestObject.getUsername())).orElseGet(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(convertUsername(requestObject.getUsername()));
            userService.save(userEntity);
            return userEntity;
        }));
        return entity;
    }
}
