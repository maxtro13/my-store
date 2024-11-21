package store.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseEntity<?> saveImage(MultipartFile file);
}
