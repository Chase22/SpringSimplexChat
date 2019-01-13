package io.github.Chase22.simplexchat.image;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository repository;

    public ImageService(final ImageRepository repository) {
        this.repository = repository;
    }

    public Optional<Image> findById(final String id) {
        return repository.findById(id).map(Image::new);
    }

    public ImageEntity save(Image image) {
        return repository.save(image.toEntity());
    }

    public File imageToFile(Image image) throws IOException {
        File file = File.createTempFile("", "png");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(Base64Utils.decodeFromString(image.getData()));
        fos.close();
        return file;
    }

    public Image fileToImage(File file) throws IOException {
        return bytesToImage(Files.readAllBytes(file.toPath()));
    }

    public Image bytesToImage(byte[] bytes) {
        return new Image(Base64Utils.encodeToString(bytes));
    }

    public List<Image> getAll() {
        return repository.findAll().stream().map(Image::new).collect(Collectors.toList());
    }
}
