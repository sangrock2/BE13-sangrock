package org.example.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherApplication {

    public static void main(String[] args) {
        String WHETHER_API_KEY = System.getenv("WEATHER_API_KEY");

        SpringApplication.run(WeatherApplication.class, args);
    }

}
