package store.dishes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(schema = "store", name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "o_file_name")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "url", length = 2048)
    private String url;

    @Column(name = "content_type")
    private String contentType;


}


