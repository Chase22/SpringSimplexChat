package org.chase.chat.simplexchat.message;

import org.chase.chat.simplexchat.chat.ChatService;
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


    public MessageService(MessageRepository messageRepository, ChatService chatService, UserService userService) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.userService = userService;
    }

    public MessageRVO getById(long id) {
        return messageRepository.findById(id).map(MessageEntity::toRVO).orElseThrow(NullPointerException::new);
    }

    public void save(Message message) {
        messageRepository.save(messagetoEntity(message));
    }

    public MessageEntity messagetoEntity(Message message) {
        MessageEntity entity = new MessageEntity();
        entity.setChat(chatService.getChatById(message.getChatid()).orElseThrow(NullPointerException::new));
        entity.setMessage(message.getMessage());
        entity.setTimestamp(message.getTimestamp());
        entity.setUser(userService.getUserById(convertUsername(message.getUsername())).orElseGet(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(convertUsername(message.getUsername()));
            userService.save(userEntity);
            return userEntity;
        }));
        return entity;
    }

}
