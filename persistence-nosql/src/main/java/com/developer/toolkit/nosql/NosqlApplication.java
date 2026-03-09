package com.developer.toolkit.nosql;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class NosqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(NosqlApplication.class, args);
    }
}
