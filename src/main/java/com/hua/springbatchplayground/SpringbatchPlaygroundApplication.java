package com.hua.springbatchplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringbatchPlaygroundApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbatchPlaygroundApplication.class, new String[]{"a=1", "b=2"});
    }
}
