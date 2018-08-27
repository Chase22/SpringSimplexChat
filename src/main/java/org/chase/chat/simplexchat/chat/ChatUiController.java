package org.chase.chat.simplexchat.chat;

import org.chase.chat.simplexchat.chatmembers.ChatUserBridgeEntity;
import org.chase.chat.simplexchat.user.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("ui/chat")
public class ChatUiController {
    private final ChatService chatService;

    public ChatUiController(ChatService chatService) {
        this.chatService = requireNonNull(chatService, "chatService");
    }

    @GetMapping("")
    public String getChatList(final Model model) {
        model.addAttribute("chats", chatService.getAllChats());
        return "chatlist";
    }

    @GetMapping("/{id}")
    public String getChatUi(final Model model, @PathVariable("id") final String id) {
        ChatEntity chat = chatService.getChatById(id);
        List<UserEntity> users = chat.getUsers().stream().map(ChatUserBridgeEntity::getUser).collect(Collectors.toList());

        model.addAttribute("chat", chat);
        model.addAttribute("users", users);
        return "chatview";
    }
}
