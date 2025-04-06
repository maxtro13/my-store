package store.dishes.service;

public interface YandexDiskService {
    String uploadFile(String path, byte[] data, String mimeType) throws Exception;
    String getPreviewUrl(String pathOnDisk) throws Exception;
}
