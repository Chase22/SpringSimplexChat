package org.chase.chat.simplexchat.chat;

import lombok.Data;
import org.chase.chat.simplexchat.user.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "chat")
public class ChatEntity {
    @Id
    @Column(name = "chat_id")
    private String id;

    @Column(name = "chat_name")
    private String name;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
    private List<UserEntity> users;

    public ChatEntity() {}

    public Chat toBusinessObject() {
        return Chat.fromEntity(this);
    }

}
