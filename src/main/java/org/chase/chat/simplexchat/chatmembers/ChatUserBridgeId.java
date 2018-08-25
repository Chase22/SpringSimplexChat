package org.chase.chat.simplexchat.chatmembers;

import lombok.Data;

@Data
class ChatUserBridgeId {
    private long chatUserId;
    private String chatId;
    private String userId;
}
