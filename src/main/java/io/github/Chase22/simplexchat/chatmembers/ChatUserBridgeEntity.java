package io.github.Chase22.simplexchat.chatmembers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Chase22.simplexchat.chat.ChatEntity;
import io.github.Chase22.simplexchat.user.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "chat_user_bridges")
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
