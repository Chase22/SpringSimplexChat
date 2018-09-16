package org.chase.chat.simplexchat.chat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;
import org.chase.chat.simplexchat.message.MessageEntity;
import org.chase.chat.simplexchat.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChatEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "chat_id")
    private String id;

    @Column(name = "chat_name")
    private String name;

    @OneToMany(mappedBy = "chat")
    private List<ChatUserBridgeEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<MessageEntity> messages;

    /**
     * Use ChatService.addUserToChat()
     */
    @Deprecated
    ChatUserBridgeEntity addUser(UserEntity user) {
        ChatUserBridgeEntity chatUserBridgeEntity = new ChatUserBridgeEntity();
        chatUserBridgeEntity.setChat(this);
        chatUserBridgeEntity.setUser(user);
        chatUserBridgeEntity.setChatId(this.getId());
        chatUserBridgeEntity.setUserId(user.getName());

        this.users.add(chatUserBridgeEntity);
        user.getChatBridges().add(chatUserBridgeEntity);

        return chatUserBridgeEntity;
    }

}
