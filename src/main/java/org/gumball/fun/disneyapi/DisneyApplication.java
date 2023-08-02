package org.gumball.fun.disneyapi;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.http.HttpClient;

import static java.lang.System.exit;

@SpringBootApplication
public class DisneyApplication implements ApplicationRunner {
    String url = "https://api.disneyapi.dev/character";

    public static void main(String[] args) {
        SpringApplication.run(DisneyApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.err.println("Running...");
        ApiClient apiClient = new ApiClient(50, url);
        apiClient.go();
        exit(1);
    }

}
