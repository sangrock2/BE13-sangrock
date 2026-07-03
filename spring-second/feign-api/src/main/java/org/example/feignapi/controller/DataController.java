package org.example.feignapi.controller;

import jakarta.annotation.PostConstruct;
import org.example.feignapi.dto.DataRequest;
import org.example.feignapi.dto.DataResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController {
    private Map<Long, DataResponse> dataStore = new HashMap<>();
    private Long idCounter = 1L;

    @PostConstruct
    public void init() {
        dataStore.put(idCounter++, new DataResponse(1L, "Item 1", 100));
        dataStore.put(idCounter++, new DataResponse(2L, "Item 2", 200));
        dataStore.put(idCounter++, new DataResponse(3L, "Item 3", 300));
        dataStore.put(idCounter++, new DataResponse(4L, "Item 4", 400));
        dataStore.put(idCounter++, new DataResponse(5L, "Item 5", 500));
    }

    @GetMapping("/{id}")
    public DataResponse getDataById(@PathVariable Long id) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) {
            throw new RuntimeException("Data not found " + id);
        }

        return dataResponse;
    }

    @PostMapping
    public DataResponse createData(@RequestBody DataRequest dataRequest) {
        DataResponse build = DataResponse.builder()
                .id(idCounter++)
                .name(dataRequest.getName())
                .value(dataRequest.getValue())
                .build();

        dataStore.put(build.getId(), build);

        return build;
    }

    @PostMapping("/{id}")
    public DataResponse updataData(@PathVariable Long id, @RequestBody DataRequest dataRequest) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) {
            throw new RuntimeException("Data not found " + id);
        }

        dataResponse.setName(dataRequest.getName());
        dataResponse.setValue(dataRequest.getValue());
        dataStore.put(id, dataResponse);

        return dataResponse;
    }

    @DeleteMapping("/{id}")
    public DataResponse deleteData(@PathVariable Long id) {
        DataResponse dataResponse = dataStore.get(id);

        if (dataResponse == null) {
            throw new RuntimeException("Data not found " + id);
        }

        dataStore.remove(id);

        return dataResponse;
    }
}
