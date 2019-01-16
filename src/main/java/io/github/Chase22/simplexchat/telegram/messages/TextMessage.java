package io.github.Chase22.simplexchat.telegram.messages;

import io.github.Chase22.simplexchat.message.Message;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.lang.String.format;

@Data
public class TextMessage implements TelegramMessage {
    private String message;

    public TextMessage(Message message) {
        this.message = formatMessage(message);
    }

    public TextMessage(String message) {
        this.message = message;
    }

    @Override
    public void execute(final AbsSender sender, final Long id) throws TelegramApiException {
        sender.execute(new SendMessage(id, message).setParseMode(ParseMode.MARKDOWN));
    }

    private String formatMessage(Message message) {
        return format("%s %n-----%n_%s_", message.getMessage(), message.getUsername());
    }
}
