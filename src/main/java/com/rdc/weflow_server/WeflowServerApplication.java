package com.rdc.weflow_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WeflowServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeflowServerApplication.class, args);
    }

}
