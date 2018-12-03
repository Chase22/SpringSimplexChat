package org.chase.chat.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;

import java.time.LocalDateTime;

import static org.chase.chat.simplexchat.user.UsernameConverter.convertUsername;

@Data
@Builder
@AllArgsConstructor
public class Message {

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("username")
    private String username;

    @JsonProperty("chatid")
    private String chatid;

    public Message(MessageEntity entity) {
        this.message = entity.getMessage();
        this.timestamp = entity.getTimestamp();
        this.username = entity.getUser().getName();
        this.chatid = entity.getChat().getId();
    }
}
