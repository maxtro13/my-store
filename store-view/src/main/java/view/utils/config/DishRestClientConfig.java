package view.utils.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import view.client.DishRestClient;
import view.client.impl.DishRestClientImpl;

@Configuration
public class DishRestClientConfig {

    @Bean
    public DishRestClient dishRestClient() {
        return new DishRestClientImpl(
                RestClient.builder()
                        .baseUrl("http://localhost:8081")
                        .build());
    }


}
