package org.chase.chat.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.user.UserEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageEntity {

    @Id
    @GeneratedValue
    private Long Id;

    private String message;

    private long timestamp;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ChatEntity chat;
}
