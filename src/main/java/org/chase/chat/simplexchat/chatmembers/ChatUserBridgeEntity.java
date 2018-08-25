package org.chase.chat.simplexchat.chatmembers;

import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.user.UserEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bridge_chat_user")
@IdClass(ChatUserBridgeId.class)
public class ChatUserBridgeEntity {
    @Id
    @Column(name = "bridge_chat_user_id")
    private long chatUserId;

    @Id
    @Column(name = "bridge_chat_id")
    private String chatId;

    @Id
    @Column(name = "bridge_user_id")
    private String userId;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bridge_user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bridge_chat_id", referencedColumnName = "chat_id")
    private ChatEntity chat;

}
