package org.chase.chat.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.user.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
