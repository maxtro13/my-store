package store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.entity.Image;
import store.service.ImageService;

@RestController
@RequestMapping("/store-api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public Image saveFile(@RequestParam(name = "file") MultipartFile file) {
        return imageService.saveImage(file);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImageById(@PathVariable("imageId") Long imageId) {
        return imageService.getImageById(imageId);
    }
}

