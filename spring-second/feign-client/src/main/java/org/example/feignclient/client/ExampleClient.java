package org.example.feignclient.client;

// Feign Client 선언부
// interface 라서 우리가 직접 구현하지 않는다
// 다른 서버로 HTTP 요청을 보내는 코드를 인터페이스 선언만으로 대신 만들어주는 것

import org.example.feignclient.dto.DataRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// @FeignClient
// name : 클라이언트의 고유 이름, 필수 값
// url : 호출한 대상 서버 주소

@FeignClient(name = "exampleClient", url = "${feign-api.url}/api/data")
public interface ExampleClient {
    @GetMapping("/{id}")
    String getData(@PathVariable("id") Long id);

    @PostMapping
    String createData(@RequestBody DataRequest dataRequest);

    @PutMapping("/{id}")
    String updateData(@PathVariable Long id, @RequestBody DataRequest dataRequest);

    @DeleteMapping("/{id}")
    String deleteData(@PathVariable Long id);
}
