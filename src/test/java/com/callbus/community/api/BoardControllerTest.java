package com.callbus.community.api;

import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import com.callbus.community.service.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(BoardController.class)
//@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 목록 api 올바르게 요청을 받아 데이터를 리턴한다.")
    void getBoardList() throws Exception {

        BoardSearchForm boardSearchForm = new BoardSearchForm();
        Pageable pageable = PageRequest.of(0, 10);

        List<BoardListDto> boardList = new ArrayList<>();

        for (int i = 1; i < 11; i++) {
            BoardListDto boardListDto = new BoardListDto((long) i, "subject : " + i, "contents : " + i, null, null,0L, "N");
            boardList.add(boardListDto);
        }
        Page<BoardListDto> data = new PageImpl<>(boardList);

        System.out.println("data = " + data.getContent());

//        when(this.boardService.getBoardList(boardSearchForm, pageable, 1L)).thenReturn(data);
        doReturn(data).when(boardService).getBoardList(boardSearchForm, pageable, 1L);
        mockMvc.perform(get("/api/v1/boards")
                        .param("page", String.valueOf(0)).param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.data.content.length()", equalTo(10)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 게시판 요청 url 입력시 에러가 발생한다.")
    void notFoundBoards() throws Exception {
        mockMvc.perform(get("/api/v1/board")
                        .param("page", String.valueOf(0)).param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("올바르지 않은(유효성체크) 데이터의 게시판 등록")
    void insertWithInValidData() throws Exception {
        mockMvc.perform(post("/api/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"subject\" : \"제목 테스트\",\n" +
                                "    \"contents\" : \"\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("올바른 데이터의 게시판 등록")
    void insertWithValidData() throws Exception {
        mockMvc.perform(post("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"subject\" : \"제목 테스트\",\n" +
                                "    \"contents\" : \"내용 테스트\"\n" +
                                "}"))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("올바르지 않은 데이터의 게시판 수정")
    void updateWithInValidData() throws Exception {
        mockMvc.perform(put("/api/v1/boards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"id\": 1,\n" +
                                "    \"memberId\" : 2,\n" +
                                "    \"subject\" : \"\",\n" +
                                "    \"contents\" : \"\",\n" +
                                "    \"isUse\" : \"true\"\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("올바른 데이터의 게시판 수정")
    void updateWithValidData() throws Exception {
        mockMvc.perform(put("/api/v1/boards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("AUTHORIZATION", "Lessor 2")
                        .content("{\n" +
                                "    \"id\": 1,\n" +
                                "    \"memberId\" : 2,\n" +
                                "    \"subject\" : \"제목 수정 테스트\",\n" +
                                "    \"contents\" : \"내용 수정 테스트\",\n" +
                                "    \"isUse\" : \"true\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }
}