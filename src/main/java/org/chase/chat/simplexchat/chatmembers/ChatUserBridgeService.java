package org.chase.chat.simplexchat.chatmembers;

import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class ChatUserBridgeService {
    private final ChatUserBridgeRepository chatUserBridgeRepository;

    public ChatUserBridgeService(ChatUserBridgeRepository chatUserBridgeRepository) {
        this.chatUserBridgeRepository = requireNonNull(chatUserBridgeRepository, "chatUserBridgeRepository");
    }

    public void save(ChatUserBridgeEntity entity) {
        chatUserBridgeRepository.save(entity);
    }
}
