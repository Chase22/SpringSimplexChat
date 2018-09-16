package org.chase.chat.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SendMessageRequestObject {

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("username")
    private String username;

    @JsonProperty("chatid")
    private String chatid;
}
