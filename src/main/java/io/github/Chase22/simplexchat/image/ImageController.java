package io.github.Chase22.simplexchat.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.IMAGE_PNG;

@Slf4j
@RestController("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public List<String> getAllId() {
        return imageService.getAll().stream().map(Image::getId).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity getImage(@PathVariable String id) {
        Optional<Image> image = imageService.findById(id);
        return image.<ResponseEntity>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {

        if (IMAGE_PNG.getMimeType().equalsIgnoreCase(file.getContentType())) {
            try {
                return ResponseEntity.ok(imageService.save(imageService.bytesToImage(file.getBytes())).getId());
            } catch (IOException e) {
                log.error("error loading file from rest", e);
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
