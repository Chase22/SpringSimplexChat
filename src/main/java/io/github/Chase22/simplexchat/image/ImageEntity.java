package io.github.Chase22.simplexchat.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Data
@AllArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "image_id")
    private String id;

    @Column(name = "image_data")
    private String data;

    public ImageEntity(final String data) {
        this.data = data;
    }
}
