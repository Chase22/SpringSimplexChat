package org.chase.chat.simplexchat.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserEntity {

    @Column(name = "user_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Id
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_passwd")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ChatUserBridgeEntity> chats = new ArrayList<>();

}
