package org.chase.chat.simplexchat.misc;

import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeService;
import org.chase.chat.simplexchat.security.ApplicationSecurity;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RestApiController("api/test")
public class TestController {
    private final ChatService chatService;
    private final UserService userService;
    private final ChatUserBridgeService chatUserBridgeService;

    public TestController(ChatService chatService, UserService userService, ChatUserBridgeService chatUserBridgeService) {
        this.chatService = chatService;
        this.userService = userService;
        this.chatUserBridgeService = chatUserBridgeService;
    }

    @PostConstruct
    public void injectTestData() {
        try {
            UserEntity testUser = new UserEntity();
            testUser.setName("user");
            testUser.setPassword(ApplicationSecurity.getPasswordEncoder().encode("password"));
            userService.insertOrUpdate(testUser);

            UserEntity testUser2 = new UserEntity();
            testUser2.setName("Name2");
            testUser2.setPassword("secret");
            userService.insertOrUpdate(testUser2);

            for (int i = 0; i < 50; i++) {
                ChatEntity chat = new ChatEntity();
                chat.setName("Test Chat " + i);

                chatService.insertOrUpdate(chat);

                chatService.addUserToChat(chat, testUser);
                chatService.addUserToChat(chat, testUser2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
