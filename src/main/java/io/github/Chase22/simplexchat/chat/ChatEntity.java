package io.github.Chase22.simplexchat.chat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.github.Chase22.simplexchat.user.UserEntity;
import lombok.Data;
import io.github.Chase22.simplexchat.chatmembers.ChatUserBridgeEntity;
import io.github.Chase22.simplexchat.message.MessageEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "chats")
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
