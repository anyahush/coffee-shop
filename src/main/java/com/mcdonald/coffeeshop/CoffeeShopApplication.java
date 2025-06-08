package com.mcdonald.coffeeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mcdonald.coffeeshop.repository")
@EntityScan(basePackages = "com.mcdonald.coffeeshop.model")
public class CoffeeShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoffeeShopApplication.class, args);
    }
}
