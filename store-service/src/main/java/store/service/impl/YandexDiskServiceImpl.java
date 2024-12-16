package store.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import store.service.YandexDiskService;

//import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class YandexDiskServiceImpl implements YandexDiskService {

    private static final Logger log = LoggerFactory.getLogger(YandexDiskServiceImpl.class);
    private final DefaultErrorAttributes errorAttributes;
    @Value("${YANDEX_OAUTH_VERIFICATION_CODE}")
    private String accessToken;

    //    private final RestClient restClient;
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Override
    public String uploadFile(String pathOnDisk, byte[] data, String mimeType) throws Exception {
        String uploadUrl = getUploaderHref(pathOnDisk);
        putFileData(uploadUrl, data, mimeType);
        return waitForPreview(pathOnDisk);
    }

    private String waitForPreview(String pathOnDisk) throws Exception {
        int maxAttempts = 10;
        int attempt = 0;
        int delay = 1000;

        while (attempt < maxAttempts) {
            try {
                String previewUrl = getPreviewUrl(pathOnDisk);
                if (previewUrl != null) {
                    return previewUrl;
                }
            } catch (Exception e) {
                log.info("Ошибка получения превью на попытке {}", attempt + 1);
                Thread.sleep(delay);
                attempt++;
            }
        }
        return null;

    }




    private String getUploaderHref(String pathOnDisk) throws Exception {
//
        String url = "/upload?path=/krolls/" + pathOnDisk + "&overwrite=true";
        var response = this.webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Response uploadHrefResponse = objectMapper.readValue(response, Response.class);
        return uploadHrefResponse.getHref();
    }

    @Override
    public String getPreviewUrl(String pathOnDisk) throws Exception {

        String url = "?path=/krolls/" + pathOnDisk + "&fields=preview&preview_size=S";
        var response = this.webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();


        JsonNode jsonNode = objectMapper.readTree(response);
        JsonNode previewNode = jsonNode.path("preview");

        if (!previewNode.isMissingNode() && !previewNode.asText().isEmpty()) {
            return previewNode.asText();
        } else {
            throw new RuntimeException("Поле 'preview' не найдено в ответе сервера.");
        }
    }


    private void putFileData(String uploadUrl, byte[] data, String mimeType) throws Exception {
        this.webClient.
                put()
                .uri(uploadUrl)
                .header("Content-Type", mimeType)
                .bodyValue(data)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    private String publishFile(String pathOnDisk) throws Exception {
        String url = "/publish?path=/krolls/" + pathOnDisk;  //+ URLEncoder.encode("/krolls/"+pathOnDisk, StandardCharsets.UTF_8);

        String response = this.webClient
                .put()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Response publicResponse = objectMapper.readValue(response, Response.class);
        return publicResponse.getHref();
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Response {
        private String href;
        private String method;
        private boolean templated;
    }


}

