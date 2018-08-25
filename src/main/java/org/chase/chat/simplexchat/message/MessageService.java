package org.chase.chat.simplexchat.message;

import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class MessageService {
    private final MessageRepository messageRepository;


    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = requireNonNull(messageRepository, "messageRepository");
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
}
