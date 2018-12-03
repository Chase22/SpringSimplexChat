package org.chase.chat.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MessageRVO {

    @Getter
    @JsonProperty("id")
    private Long Id;

    @Getter
    @JsonProperty("message")
    private String message;

    @Getter
    @JsonProperty("timestamp")
    private long timestamp;

    @Getter
    @JsonProperty("userId")
    private String user;

    @Getter
    @JsonProperty("chatId")
    private String chat;

    public Message toMessage() {
        return Message.builder().chatid(chat).message(message).timestamp(timestamp).username(user).build();
    }

}
