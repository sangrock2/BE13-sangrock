package org.example.basicboard.controller;

import org.example.basicboard.exception.DuplicateUserIdException;
import org.example.basicboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 프레젠테이션 계층 테스트 - 컨트롤러의 Http 계약을 검증
// URL 매핑, 요청 본문 파싱, 상태 코드, 응답 json, 예외, 상태 코드 변환을 검증
// 비즈니스 로직이 아니라 웹 껍데기가 제대로 동작하는가

// @WebMvcTest
// 웹 계층만 뜨는 슬라이스 테스트
// 컨트롤러가 의존하는 MemberService는 진짜가 없다 @MocBean으로 가짜를 주입

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    // 실제 섭저를 띄우지 않고, Http 요청을 흉내 내서 컨트롤러에 넣어주는 도구
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공 - 200과 이동할 url을 반환한다")
    void join_성공() throws Exception {
        String requestJson = objectMapper.writeValueAsString(
                Map.of(
                        "userId", "test1",
                        "password", "1234",
                        "name", "test"
                )
        );

        // when & then
        // mockMvc.perform : 가짜 HTTP 요청 한 번을 만들어 컨트롤러에 넣는다
        // mockMvc.perform(요청만들기).andExpect(기대검증) ...
        // perform(요청): 요청을 실행한다. 실제 톰켓 없이 스프링Mvc 내부로 요청을 흘려보낸다
        // andExpect(기대검증) : 그 결과가 기대에 맞는지 검증한다

        // perform 안의 요청 만들기 (RequestBuilder)
        // - post("/api/members/join") : POST 메서드 + 이 URL 로 요청. (get/put/delete/multipart 등도 있다)
        // - .contentType(APPLICATION_JSON) : 요청 헤더 Content-Type 지정 = "본문은 JSON 이다"
        // - .content(requestJson)          : 요청 본문(body). 컨트롤러의 @RequestBody 가 이걸 받아 파싱한다
        //   (폼 전송을 흉내 낼 땐 .param("key","value"), 파일은 multipart(...).file(...) 를 쓴다)

        // 결과 검증 (ResultMatcher)
        // - status().isOk()            : 응답 상태코드가 200 인가 (isConflict()=409, isNotFound()=404 ...)
        // - jsonPath("$.url").value(..): 응답 JSON 본문에서 $.url 값이 기대와 같은가
        //   ($ 는 JSON 루트. $.url 은 최상위 url 필드, $.list[0].name 처럼 깊이 파고들 수도 있다)
        mockMvc.perform(
                post("/api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("/members/login"));
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디가 중복이면 409와 에러 메시지를 반환한다.")
    void join_중복이면_409() throws Exception {
        willThrow(new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다."))
                .given(memberService).join(any());

        String requestJson = objectMapper.writeValueAsString(
                Map.of(
                        "userId", "test1",
                        "password", "1234",
                        "name", "test"
                )
        );

        mockMvc.perform(
                post("api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("[회원가입] 이미 존재하는 아이디입니다."));


    }



}