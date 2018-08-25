package org.chase.chat.simplexchat.ChatMembers;

import lombok.Data;

@Data
class ChatUserBridgeId {
    private long chatUserId;
    private String chatId;
    private String userId;
}
