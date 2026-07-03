package org.example.weather.dto;

public record Body(
        Items items,
        int pageNo,
        int numOfRows,
        int totalCount
) {
}
