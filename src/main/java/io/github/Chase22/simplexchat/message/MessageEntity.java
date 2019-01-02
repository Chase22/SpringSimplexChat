package io.github.Chase22.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.Chase22.simplexchat.chat.ChatEntity;
import io.github.Chase22.simplexchat.user.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "messages")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    private long timestamp;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ChatEntity chat;

    public MessageRVO toRVO() {
        if (user == null || chat == null) {
            return null;
        } else {
            return new MessageRVO(id, message, timestamp, user.getName(), chat.getId());
        }
    }
}
