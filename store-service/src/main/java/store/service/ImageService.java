package store.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String saveImage(MultipartFile file);

//    ResponseEntity<Image> getImageById(Long imageId);
}
