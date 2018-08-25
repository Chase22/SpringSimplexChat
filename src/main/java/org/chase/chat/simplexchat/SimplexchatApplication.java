package org.chase.chat.simplexchat;

import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SimplexchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplexchatApplication.class, args);
    }
}
