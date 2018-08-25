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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatEntity> chats;

}
