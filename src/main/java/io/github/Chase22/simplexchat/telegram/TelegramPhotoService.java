package io.github.Chase22.simplexchat.telegram;

import name.maratik.spring.telegram.LongPollingTelegramBotService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class TelegramPhotoService {
    private final DefaultAbsSender bot;

    public TelegramPhotoService(final LongPollingTelegramBotService botService) {
        this.bot = botService.getClient();
    }

    public java.io.File getImageByPhotoSize(List<PhotoSize> photos) {
        return downloadPhotoByFilePath(getFilePath(getPhoto(photos)));
    }

    private String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.hasFilePath()) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else { // If not, let find it
            // We create a GetFile method and set the file_id from the photo
            GetFile getFileMethod = new GetFile();
            getFileMethod.setFileId(photo.getFileId());
            try {
                // We execute the method using AbsSender::execute method.
                File file = bot.execute(getFileMethod);
                // We now have the file_path
                return file.getFilePath();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        return null; // Just in case
    }

    private java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return bot.downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PhotoSize getPhoto(List<PhotoSize> photos) {
        // We fetch the bigger photo
        return photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);

    }
}
