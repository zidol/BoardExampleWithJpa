package com.callbus.community.api;

import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import com.callbus.community.service.heart.HeartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(HeartController.class)
class HeartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    HeartService heartService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    BoardRepository boardRepository;

    @Test
    @DisplayName("올바르지 않은(유효성체크) 데이터의 좋아요 등록")
    void insertWithInValidData() throws Exception {
        mockMvc.perform(post("/api/v1/hearts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"boardId\" :\"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("올바른 데이터의 좋아요 등록")
    void insertWithValidData() throws Exception {
        mockMvc.perform(post("/api/v1/hearts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"boardId\" :\"1\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

}