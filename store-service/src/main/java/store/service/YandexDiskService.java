package store.service;

public interface YandexDiskService {
    String uploadFile(String path, byte[] data, String mimeType) throws Exception;
}
