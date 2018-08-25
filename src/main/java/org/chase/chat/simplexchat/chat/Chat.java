package org.chase.chat.simplexchat.chat;

import lombok.Data;
import org.chase.chat.simplexchat.user.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Chat {
    private String id;
    private String name;


    private List<String> users;

    public static Chat fromEntity(ChatEntity entity) {
        Chat chat = new Chat();
        chat.setId(entity.getId());
        chat.setName(entity.getId());
        chat.setUsers(entity.getUsers().stream().map(UserEntity::getId).collect(Collectors.toList()));
        return chat;
    }

    public void addUser(final String userID) {
        users.add(userID);
    }

    public void removeUser(final String userID) {
        users.remove(userID);
    }
}
