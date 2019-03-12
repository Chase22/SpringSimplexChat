package io.github.Chase22.simplexchat.chat;

import io.github.Chase22.simplexchat.message.MessageRVO;

import java.util.List;

public class MessageListRvo {
    private List<MessageRVO> messages;

    public MessageListRvo(final List<MessageRVO> messages) {
        this.messages = messages;
    }

    public List<MessageRVO> getMessages() {
        return messages;
    }

    public void setMessages(final List<MessageRVO> messages) {
        this.messages = messages;
    }
}
