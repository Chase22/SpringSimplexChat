package io.github.Chase22.simplexchat.telegram;

import io.github.Chase22.simplexchat.image.ImageService;
import io.github.Chase22.simplexchat.message.Message;
import io.github.Chase22.simplexchat.telegram.messages.ImageMessage;
import io.github.Chase22.simplexchat.telegram.messages.TelegramMessage;
import io.github.Chase22.simplexchat.telegram.messages.TextMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TelegramMessageHandler {
    private final ImageService imageService;

    public TelegramMessageHandler(final ImageService imageService) {
        this.imageService = imageService;
    }

    public TelegramMessage handleMessage(Message message) {
        if (message.getMessage().startsWith("{{image}}")) {
            return handleImage(message);
        } else {
            return handleText(message);
        }
    }

    private TextMessage handleText(Message message) {
        return new TextMessage(message);
    }

    private TelegramMessage handleImage(Message message) {
        String id = message.getMessage().replace("{{image}}", "");

        return imageService.findById(id).map(image -> {
            try {
                return new ImageMessage(imageService.imageToFile(image), message.getUsername());
            } catch (IOException e) {
                return new TextMessage("Error Loading image");
            }
        }).orElse(new TextMessage("Image not found"));
    }
}
