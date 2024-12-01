package view.entity;

public record Image(Long id, String fileName, String originalFileName, Long size, String filePath, String contentType) {
}
