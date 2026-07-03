package org.example.feignapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class DataResponse {
    private long id;
    private String name;
    private int value;
}
