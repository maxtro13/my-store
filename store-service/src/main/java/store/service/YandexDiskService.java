package store.service;

public interface YandexDiskService {
    //todo понять почему иногда не возвращается публичная ссылка
    String uploadFile(String path, byte[] data, String mimeType) throws Exception;
    String getPreviewUrl(String pathOnDisk) throws Exception;
}
