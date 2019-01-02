package io.github.Chase22.simplexchat.chatmembers;

import lombok.Data;

import java.io.Serializable;

@Data
class ChatUserBridgeId implements Serializable {

    private long chatUserId;
    private String chatId;
    private String userId;
}
