package store.dishes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class YandexRestClientConfig {

    @Bean
    public RestClient restClient(@Value("${YANDEX_OAUTH_VERIFICATION_CODE}") String accessToken) {
        return RestClient.builder()
                .baseUrl("https://cloud-api.yandex.net/v1/disk/resources")
                .defaultHeader("Authorization", "OAuth " + accessToken)
                .build();

    }

}