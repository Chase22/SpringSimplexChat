package io.github.Chase22.simplexchat.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("username")
    private String username;

    @JsonProperty("chatid")
    private String chatid;

    public Message() {}

    public Message(MessageEntity entity) {
        this.message = entity.getMessage();
        this.timestamp = entity.getTimestamp();
        this.username = entity.getUser().getName();
        this.chatid = entity.getChat().getId();
    }

    public Message(final String message, final long timestamp, final String username, final String chatid) {
        this.message = message;
        this.timestamp = timestamp;
        this.username = username;
        this.chatid = chatid;
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public String getMessage() {
        return this.message;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getUsername() {
        return this.username;
    }

    public String getChatid() {
        return this.chatid;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setChatid(final String chatid) {
        this.chatid = chatid;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Message)) return false;
        final Message other = (Message) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        if (this.getTimestamp() != other.getTimestamp()) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$chatid = this.getChatid();
        final Object other$chatid = other.getChatid();
        if (this$chatid == null ? other$chatid != null : !this$chatid.equals(other$chatid)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final long $timestamp = this.getTimestamp();
        result = result * PRIME + (int) ($timestamp >>> 32 ^ $timestamp);
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $chatid = this.getChatid();
        result = result * PRIME + ($chatid == null ? 43 : $chatid.hashCode());
        return result;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Message;
    }

    public String toString() {
        return "Message(message=" + this.getMessage() + ", timestamp=" + this.getTimestamp() + ", username=" + this.getUsername() + ", chatid=" + this.getChatid() + ")";
    }

    public static class MessageBuilder {
        private String message;
        private long timestamp;
        private String username;
        private String chatid;

        MessageBuilder() {
        }

        public Message.MessageBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public Message.MessageBuilder timestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Message.MessageBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public Message.MessageBuilder chatid(final String chatid) {
            this.chatid = chatid;
            return this;
        }

        public Message build() {
            return new Message(message, timestamp, username, chatid);
        }

        public String toString() {
            return "Message.MessageBuilder(message=" + this.message + ", timestamp=" + this.timestamp + ", username=" + this.username + ", chatid=" + this.chatid + ")";
        }
    }
}
