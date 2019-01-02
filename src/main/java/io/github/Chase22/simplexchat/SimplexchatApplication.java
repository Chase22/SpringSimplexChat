package io.github.Chase22.simplexchat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SimplexchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplexchatApplication.class, args);
    }
}
