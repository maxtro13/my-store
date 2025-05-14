package store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "store.orders.repository")
public class StoreServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }
}
