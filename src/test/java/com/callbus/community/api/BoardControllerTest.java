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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
            BoardListDto boardListDto = new BoardListDto((long) i, "subject : " + i, "contents : " + i,0L, "N");
            boardList.add(boardListDto);
        }
        Page<BoardListDto> data = new PageImpl<>(boardList);


//        when(this.boardService.getBoardList(boardSearchForm, pageable, 1L)).thenReturn(data);
        doReturn(data).when(boardService).getBoardList(boardSearchForm, pageable, 1L);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/boards")
                        .param("page", String.valueOf(0)).param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 게시판 요청 url 입력시 에러가 발생한다.")
    void notFoundBoards() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/board")
                        .param("page", String.valueOf(0)).param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}