package org.chase.chat.simplexchat.message;

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
public class MessageEntity {

    @Id
    @GeneratedValue
    private Long Id;

    private String message;

    private LocalDateTime timestamp;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private ChatEntity chat;
}
