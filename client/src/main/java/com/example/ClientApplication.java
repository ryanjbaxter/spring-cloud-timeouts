package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@RestController
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @FeignClient(value = "sleep")
    interface SleepClient {

        @RequestMapping(value = "/sleep")
        String sleep();
    }

    private SleepClient sleepClient;

    public ClientApplication(SleepClient sleepClient) {
        this.sleepClient = sleepClient;
    }

    @RequestMapping(value = "/test")
    public String getGoogle() {
        // If no timeout
        return sleepClient.sleep();
    }

    @RequestMapping(value = "/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(1000);
        return "OK";
    }
}