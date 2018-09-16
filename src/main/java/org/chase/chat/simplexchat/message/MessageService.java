package org.chase.chat.simplexchat.message;

import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;


    public MessageService(MessageRepository messageRepository, ChatService chatService, UserService userService) {
        this.messageRepository = requireNonNull(messageRepository, "messageRepository");
        this.chatService = requireNonNull(chatService,"chatService");
        this.userService = requireNonNull(userService,"userService");
    }

    public MessageEntity getById(long id) {
        return messageRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public void insertOrUpdateMessage(MessageEntity entity) {
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
        entity.setUser(userService.getUserById(requestObject.getUsername()).orElseThrow(NullPointerException::new));
        return entity;
    }
}
