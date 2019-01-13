package io.github.Chase22.simplexchat.image;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {
    private String id;
    private String data;

    public Image(ImageEntity entity) {
        this.id = entity.getId();
        this.data = entity.getData();
    }

    public Image(ImageRVO rvo) {
        this.id = rvo.getId();
        this.data = rvo.getData();
    }

    public Image(final String data) {
        this.data = data;
    }

    public ImageEntity toEntity() {
        return new ImageEntity(id, data);
    }

    public ImageRVO toRVO() {
        return new ImageRVO(id, data);
    }
}
