package org.chase.chat.simplexchat.ChatMembers;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(ChatUserBridgeId.class)
public class ChatUserBridgeEntity {
    @Id
    @Column(name = "chat_user_id")
    private long chatUserId;

    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Id
    @Column(name = "user_id")
    private String userId;
}
