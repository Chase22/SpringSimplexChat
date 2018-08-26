package org.chase.chat.simplexchat.misc;

import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;
import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeService;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RestApiController("test")
public class TestController {
    private final ChatService chatService;
    private final UserService userService;
    private final ChatUserBridgeService chatUserBridgeService;

    public TestController(ChatService chatService, UserService userService, ChatUserBridgeService chatUserBridgeService) {
        this.chatService = chatService;
        this.userService = userService;
        this.chatUserBridgeService = chatUserBridgeService;
    }

    @GetMapping("test")
    public void testDatabase() {
        try {
            UserEntity testUser = new UserEntity();
            testUser.setName("Name");
            testUser.setPassword("secret");

            for (int i = 0; i < 50; i++) {
                ChatEntity chat = new ChatEntity();
                chat.setName("Test Chat " + i);

                chatService.insertOrUpdate(chat);
            }

            /*
            chat.addUser(testUser);
            chat2.addUser(testUser);

            chat.getUsers().forEach(chatUserBridgeService::save);
            chat2.getUsers().forEach(chatUserBridgeService::save);
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
