package org.example.weather.dto;

public record Item(
        String baseDate,
        String baseTime,
        String category,
        int nx,
        int ny,
        String obsrValue
) {
}
