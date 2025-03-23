package store.dishes.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.dishes.service.ImageService;
import store.dishes.service.YandexDiskService;

@RestController
@RequestMapping("/store-api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);
    private final YandexDiskService yandexDiskService;

    private final ImageService imageService;

    @PostMapping("/create")
    public ResponseEntity<?> createImage(@RequestParam(name = "image") MultipartFile image) throws Exception {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            String filePathOnDisk = "food_image_" + image.getOriginalFilename();
            this.yandexDiskService.uploadFile(filePathOnDisk, image.getBytes(), image.getContentType());

            return ResponseEntity.status(HttpStatus.CREATED).body(this.yandexDiskService.getPreviewUrl(filePathOnDisk));

        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/publicUrl")
    public ResponseEntity<?> getImage() throws Exception {
        return ResponseEntity.ok(this.yandexDiskService.getPreviewUrl("food_image_up.png"));
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable(name = "imageId") Long imageId) throws Exception {
        return ResponseEntity.ok(this.imageService.getImageById(imageId));
    }
}

