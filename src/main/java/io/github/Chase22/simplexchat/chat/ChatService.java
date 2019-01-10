package io.github.Chase22.simplexchat.chat;

import io.github.Chase22.simplexchat.chatmembers.ChatUserBridgeService;
import io.github.Chase22.simplexchat.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatUserBridgeService chatUserBridgeService;

    public ChatService(final ChatRepository chatRepository, ChatUserBridgeService chatUserBridgeService) {
        this.chatRepository = requireNonNull(chatRepository, "chatRepository");
        this.chatUserBridgeService = requireNonNull(chatUserBridgeService, "chatUserBridgeService");
    }

    public Optional<ChatEntity> getChatById(final String id) {
        return chatRepository.findById(id);
    }

    public List<ChatEntity> getAllChats() {
        return chatRepository.findAll();
    }

    public void save(final ChatEntity chatEntity) {
        chatRepository.save(chatEntity);
    }

    public void deleteChatById(String id) {
        chatRepository.deleteById(id);
    }

    @SuppressWarnings("Deprecated")
    public void addUserToChat(ChatEntity chat, UserEntity user) {
        chatUserBridgeService.save(chat.addUser(user));
    }

}
