package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.service.YandexDiskService;

@RestController
@RequestMapping("/store-api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final YandexDiskService yandexDiskService;

//    private final ImageService imageService;

    @PostMapping("/create")
    public ResponseEntity<?> createImage(@RequestParam(name = "image") MultipartFile image) throws Exception {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            String filePathOnDisk = "food_image_".concat(image.getOriginalFilename());

            imageUrl = yandexDiskService.uploadFile(filePathOnDisk, image.getBytes(), image.getContentType());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    }
}

