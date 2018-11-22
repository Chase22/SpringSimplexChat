package org.chase.chat.simplexchat.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserEntity {

    @Id
    @Column(name = "user_name")
    private String name;

    @Column(name = "user_passwd")
    private String password;

    @Column(name = "user_telegram_id")
    private Long telegramId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatUserBridgeEntity> chats = new ArrayList<>();

    public List<ChatEntity> getChats() {
        return chats.stream().map(ChatUserBridgeEntity::getChat).collect(Collectors.toList());
    }

    public List<ChatUserBridgeEntity> getChatBridges() {
        return chats;
    }

}
