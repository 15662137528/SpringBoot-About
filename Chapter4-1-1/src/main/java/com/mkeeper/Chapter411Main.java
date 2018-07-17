package com.mkeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class Chapter411Main {

    public static void main(String[] args) {
        SpringApplication.run(Chapter411Main.class, args);
    }
}
