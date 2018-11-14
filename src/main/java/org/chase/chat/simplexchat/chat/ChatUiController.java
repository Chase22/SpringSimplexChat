package org.chase.chat.simplexchat.chat;

import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;
import org.chase.chat.simplexchat.user.UserEntity;
import org.chase.chat.simplexchat.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("ui/chat")
public class ChatUiController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatUiController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getChatList(final Model model, final Principal principal) {
        UserEntity user = userService.getUserById(principal.getName()).orElseThrow(NullPointerException::new);
        List<ChatEntity> chats = user.getChats();
        model.addAttribute("chats", chats);
        return "chatlist";
    }

    @GetMapping("/{id}")
    public String getChatUi(@PathVariable("id") final String id, final Model model, final Principal principal) {
        ChatEntity chat = chatService.getChatById(id).orElseThrow(NullPointerException::new);
        List<UserEntity> users = chat.getUsers().stream().map(ChatUserBridgeEntity::getUser).collect(Collectors.toList());

        model.addAttribute("userID", principal.getName());
        model.addAttribute("chat", chat);
        model.addAttribute("users", users);
        return "chatview";
    }

    @GetMapping
    public String getChatUi(final Model model) {
        ChatEntity chat = chatService.getAllChats().get(0);

        model.addAttribute("chat", chat);

        return "chatview";
    }
}
