package edu.poly.ASM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "edu.poly.ASM.repository")
@EntityScan(basePackages = "edu.poly.ASM.entity")
public class GamingShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamingShopApplication.class, args);
    }
}
