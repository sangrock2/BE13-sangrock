package org.example.weather;

import lombok.RequiredArgsConstructor;
import org.example.weather.dto.Header;
import org.example.weather.dto.Item;
import org.example.weather.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;

    @Value("${weather.api.key}")
    private String WEAHTER_API_KEY;

    public List<Item> getWeather(int nx, int ny) {
        LocalDateTime now = LocalDateTime.now();

        if (now.getMinute() < 40) {
            now = now.minusMinutes(1);
        }

        int numOfRows = 10;
        int pageNo = 1;
        String dateType = "JSON";
        String base_date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String base_time = now.format(DateTimeFormatter.ofPattern("HH00"));

        WeatherResponse response = weatherClient.getUltraSrtNcst(
                WEAHTER_API_KEY, numOfRows, pageNo, dateType, base_date, base_time, nx, ny
        );

        Header header = response.response().header();

        if (!Objects.equals(header.resultCode(), "00")){
            throw new RuntimeException("Error getting weather data");
        }

        return response.response().body().items().item();
    }

    public List<String> readableWeather(int nx, int ny) {
        List<Item> weather = getWeather(nx, ny);
        List<String> items = new ArrayList<>();

        for (Item item : weather) {
            String category = item.category();
            String obsrvalue = item.obsrValue();

            switch (category) {
                case "T1H" -> items.add("Temperature : " + obsrvalue);
                case "REH" -> items.add("humidity : " + obsrvalue);
                case "RN1" -> items.add("1-hour precipitation : " + obsrvalue);
                case "WSD" -> items.add("wind speed : " + obsrvalue);
                case "PTY" -> items.add("type of precipitation : " + pty(obsrvalue));
            }
        }

        System.out.println(items);

        return items;
    }

    public String pty(String obsrvalue) {
        String pty = "None";

        switch (obsrvalue) {
            case "0" -> pty = "None";
            case "1" -> pty = "Rain";
            case "2" -> pty = "Rain/Snow";
            case "3" -> pty = "Snow";
            case "5" -> pty = "Raindrops";
            case "6" -> pty = "Raindrops + snow";
            case "7" -> pty = "Snowdrops";
        }

        return pty;
    }
}
