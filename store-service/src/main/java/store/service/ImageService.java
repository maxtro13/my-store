package store.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import store.entity.Image;

public interface ImageService {

    Image saveImage(MultipartFile file);
}
