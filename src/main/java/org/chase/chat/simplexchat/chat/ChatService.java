package org.chase.chat.simplexchat.chat;

import org.apache.commons.collections4.IteratorUtils;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeService;
import org.chase.chat.simplexchat.user.UserEntity;
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
        return IteratorUtils.toList(chatRepository.findAll().iterator());
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
