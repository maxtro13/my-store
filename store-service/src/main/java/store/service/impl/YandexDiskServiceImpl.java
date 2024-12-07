package store.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import store.service.YandexDiskService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class YandexDiskServiceImpl implements YandexDiskService {

    @Value("${yandex.oauth.verification-code}")
    private String accessToken;

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Override
    public String uploadFile(String pathOnDisk, byte[] data, String mimeType) throws Exception {
        String uploadUrl = getUploaderHref(pathOnDisk);
        putFileData(uploadUrl, data, mimeType);
//        this.restClient.get()
//                .uri(publishFile(pathOnDisk))
//                .retrieve()
//                .body(String.class);
//        return  this.restClient.get()
//                .uri(publishFile(pathOnDisk))
//                .retrieve()
//                .body(String.class);
        publishFile(pathOnDisk);
        return pathOnDisk;
    }
//todo добавить возможность получения публичной ссылки по публичному ключу
    private String getUploaderHref(String pathOnDisk) throws Exception {
//        String encodedPath = URLEncoder.encode("/krolls/"+pathOnDisk, StandardCharsets.UTF_8);
        String encodedPath="/krolls/"+pathOnDisk;
        String url = "/upload?path=" + encodedPath + "&overwrite=true";
        var response = this.restClient
                .get()
                .uri(url)
                .retrieve()
                .body(String.class);
        Response uploadHrefResponse = objectMapper.readValue(response, Response.class);
        return uploadHrefResponse.getHref();
    }

    private void putFileData(String uploadUrl, byte[] data, String mimeType) {
        String response = this.restClient.
                put()
                .uri(uploadUrl)
                .header("Content-Type", mimeType)
                .body(data)
                .retrieve()
                .body(String.class);

    }
//    private void moveFileData(String uploadUrl, byte[] data, String mimeType) {
//        this.restClient
//                .post()
//                .uri("/move?from="+uploadUrl+"&path="+"/krolls")
//                .header("Content-Type", mimeType)
//                .body(data)
//                .retrieve()
//                .body(String.class);

    private String publishFile(String pathOnDisk) throws Exception {
        String url = "/publish?path=/krolls/"+pathOnDisk;  //+ URLEncoder.encode("/krolls/"+pathOnDisk, StandardCharsets.UTF_8);

        String response = this.restClient
                .put()
                .uri(url)
                .retrieve()
                .body(String.class);
        Response publicResponse = objectMapper.readValue(response, Response.class);
        return publicResponse.getHref();
    }

    //todo сделать так, чтобы файл скачивался в папку, а не просто на диск
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Response {
        private String href;
        private String method;
        private boolean templated;
    }


}

