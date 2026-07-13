package org.example.spring_mission_join.controller;

import org.example.spring_mission_join.dto.MemberJoinRequestDto;
import org.example.spring_mission_join.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApiController.class)
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;


    private String requestJson;

    @BeforeEach
    void setUp() throws Exception {
        MemberJoinRequestDto requestDto = new MemberJoinRequestDto();

        requestDto.setUserId("test1");
        requestDto.setPassword("1234");
        requestDto.setUserName("홍길동");

        requestJson = objectMapper.writeValueAsString(requestDto);
    }

    @Test
    void join_success() throws Exception {
        mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andExpect(status().isOk()).andExpect(jsonPath("$.url").value("/members/login"));
    }

    @Test
    void join_fail() throws Exception {

    }
}
