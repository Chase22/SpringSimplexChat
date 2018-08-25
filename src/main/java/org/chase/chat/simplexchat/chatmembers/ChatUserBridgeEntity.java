package org.chase.chat.simplexchat.chatmembers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.user.UserEntity;

import javax.persistence.*;

@Data
@Entity
@Table
@IdClass(ChatUserBridgeId.class)
public class ChatUserBridgeEntity {

    @Id
    @GeneratedValue
    @Column(name = "bridge_chat_user_id")
    private long chatUserId;

    @Id
    @Column(name = "bridge_chat_id")
    private String chatId;

    @Id
    @Column(name = "bridge_user_id")
    private String userId;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bridge_user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bridge_chat_id", referencedColumnName = "chat_id")
    private ChatEntity chat;

}
