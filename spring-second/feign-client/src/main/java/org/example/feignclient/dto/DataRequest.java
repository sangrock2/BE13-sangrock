package org.example.feignclient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DataRequest {
    private String name;
    private int value;
}
