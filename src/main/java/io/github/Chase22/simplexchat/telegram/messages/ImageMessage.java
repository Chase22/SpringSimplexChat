package io.github.Chase22.simplexchat.telegram.messages;

import lombok.Getter;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Comparator;

public class ImageMessage implements TelegramMessage {

    @Getter
    private final String sender;

    @Getter
    private String fileId;

    @Getter
    private File photo;

    public ImageMessage(final File photo, String sender) {
        this.photo = photo;
        this.sender = sender;
    }

    @Override
    public void execute(final AbsSender sender, final Long id) throws TelegramApiException {
        if (StringUtils.isEmpty(fileId)) {
            fileId = sender.execute(new SendPhoto().setPhoto(photo).setChatId(id).setCaption(this.sender))
                    .getPhoto()
                    .stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId).orElse("");
        } else {
            sender.execute(new SendPhoto().setPhoto(fileId).setChatId(id));
        }
    }
}
