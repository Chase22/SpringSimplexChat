package io.github.Chase22.simplexchat.message;

import io.github.Chase22.simplexchat.chat.ChatService;
import io.github.Chase22.simplexchat.user.UserEntity;
import io.github.Chase22.simplexchat.user.UserService;
import io.github.Chase22.simplexchat.user.UsernameConverter;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

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
        entity.setUser(userService.getUserById(UsernameConverter.convertUsername(message.getUsername())).orElseGet(() -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(UsernameConverter.convertUsername(message.getUsername()));
            userService.save(userEntity);
            return userEntity;
        }));
        return entity;
    }

}
