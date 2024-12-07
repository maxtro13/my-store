//package store.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//import store.repositories.ImageRepository;
//import store.service.ImageService;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ImageServiceImpl implements ImageService {
//
//    private final ImageRepository imageRepository;
//
//    @Value("${spring.servlet.multipart.location}")
//    private String fileDirectory;
//
//    @Override
//    @Transactional
//    public String saveImage(MultipartFile file) {
//        if (file.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot save empty file");
//        }
//        String fileName = generateUniqueFileName(file.getOriginalFilename());
//        String filePath = Paths.get(fileDirectory, fileName).toString();
////         image = new Image();
//
//        try {
//            file.transferTo(new File(filePath));
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//
//    @Override
//    @Transactional
//    public ResponseEntity<Image> getImageById(Long imageId) {
//
//        return ResponseEntity.ok(
//                imageRepository.findById(imageId)
//                        .orElseThrow(
//                                () -> new ResponseStatusException(
//                                        HttpStatus.NOT_FOUND, "Image not found")));
//    }
//
//
//    private String generateUniqueFileName(String originalFilename) {
//        return UUID.randomUUID().toString() + "." + originalFilename;
//    }
//}
