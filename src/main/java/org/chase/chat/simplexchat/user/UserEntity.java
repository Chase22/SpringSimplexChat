package org.chase.chat.simplexchat.user;

import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "user")
public class UserEntity {
    @Column(name = "user_id")
    @Id
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_passwd")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_chat",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    )
    private List<ChatEntity> chats;

}
