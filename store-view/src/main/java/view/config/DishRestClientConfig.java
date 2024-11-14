package view.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;
import view.client.DishRestClient;
import view.client.impl.DishRestClientImpl;

@Configuration
public class DishRestClientConfig {

    @Bean
    public DishRestClient dishRestClient() {
        return new DishRestClientImpl(
                RestClient.builder()
                        .messageConverters(httpMessageConverters ->
                                httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                        .baseUrl("http://localhost:8081")
                        .build());
    }
}
