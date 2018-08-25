package org.chase.chat.simplexchat.chat;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(final ChatRepository chatRepository) {
        this.chatRepository = requireNonNull(chatRepository, "chatRepository");
    }

    public ChatEntity getChatById(final String id) {

        return chatRepository.findById(id).orElseThrow(() -> new NullPointerException("Chat with id "+id+" could not be found"));
    }

    public Iterable<ChatEntity> getAllChats() {
        return chatRepository.findAll();
    }

    public void insertOrUpdate(final ChatEntity chatEntity) {
        chatRepository.save(chatEntity);
    }

    public void deleteChatById(String id) {
        chatRepository.deleteById(id);
    }

}
