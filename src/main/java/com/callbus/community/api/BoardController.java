package com.callbus.community.api;

import com.callbus.community.domain.Board;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.dto.common.MemberInfo;
import com.callbus.community.dto.common.ResponseData;
import com.callbus.community.dto.common.StatusEnum;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.service.board.BoardService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<Object> getBoards(BoardSearchForm boardSearchForm
            , Pageable pageable, HttpServletRequest request) {

        log.info("게시판 목록 조회");
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        Page<BoardListDto> boardList = boardService.getBoardList(boardSearchForm, pageable, memberInfo == null ? null : memberInfo.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        ResponseData data = new ResponseData();
        data.setStatus(StatusEnum.OK);
        data.setMessage("정상적으로 조회 하셨습니다");
        data.setData(boardList);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

}
