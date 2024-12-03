package view.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import view.client.StoreRestClient;
import view.client.impl.StoreRestClientImpl;

@Configuration
public class DishRestClientConfig {

    @Bean
    public StoreRestClient dishRestClient() {
        return new StoreRestClientImpl(
                RestClient.builder()
                        .baseUrl("http://localhost:8081")
                        .build());
    }


}
