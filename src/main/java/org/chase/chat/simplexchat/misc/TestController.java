package org.chase.chat.simplexchat.misc;

import org.chase.chat.simplexchat.chat.ChatEntity;
import org.chase.chat.simplexchat.chat.ChatService;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Component
@RestApiController("test")
public class TestController {
    private final ChatService chatService;

    private final UserService userService;

    public TestController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("test")
    public void testDatabase() {
        try {
            UserEntity testUser = new UserEntity();
            testUser.setId("test");
            testUser.setName("Name");
            testUser.setPassword("secret");

            ChatEntity chat = new ChatEntity();
            chat.setId("testChat");
            chat.setName("Test Chat");

            chatService.insertOrUpdate(chat);

            ChatEntity chat2 = new ChatEntity();
            chat2.setId("testChat2");
            chat2.setName("New Test Chat");

            chatService.insertOrUpdate(chat2);

            List<ChatEntity> chats = Arrays.asList(chat, chat2);
            testUser.setChats(chats);

            userService.insertOrUpdate(testUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
