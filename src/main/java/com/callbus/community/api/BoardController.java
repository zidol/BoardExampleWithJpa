package com.callbus.community.api;

import com.callbus.community.domain.Board;
import com.callbus.community.dto.board.BoardDto;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.dto.common.MemberInfo;
import com.callbus.community.dto.common.ResponseData;
import com.callbus.community.dto.common.StatusEnum;
import com.callbus.community.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<Object> getBoards(BoardSearchForm boardSearchForm
            , Pageable pageable, HttpServletRequest request) throws Exception {

        log.info("게시판 목록 조회");
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        Page<BoardListDto> boardList = boardService.getBoardList(boardSearchForm, pageable, memberInfo == null ? null : memberInfo.getId());

        return setObjectResponseEntity(boardList, "정상적으로 등록 하셨습니다");
    }

    @PostMapping("/boards")
    public ResponseEntity<Object> insertBoard(@RequestBody @Valid BoardDto boardDto, HttpServletRequest request) throws Exception {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        boardDto.setMemberId(memberInfo.getId());

        System.out.println("boardDto.toString() = " + boardDto.toString());
        Board insertBoard = boardService.insertBoard(boardDto);

        return setObjectResponseEntity(null, "정상적으로 등록 하셨습니다");
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<Object> updateBoard(@PathVariable("id") Long id,
                                              @RequestBody @Valid BoardUpdateForm boardForm) throws Exception {

        boardForm.setId(id);
        boardService.updateBoard(boardForm);

        return setObjectResponseEntity(null, "정상적으로 수정 하였습니다.");

    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Object> deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        BoardUpdateForm boardForm = new BoardUpdateForm();
        boardForm.setMemberId(memberInfo.getId());
        boardForm.setId(id);
        boardForm.setIsUse(false);

        boardService.deleteBoard(boardForm);

        return setObjectResponseEntity(null, "정상적으로 삭제 하였습니다.");

    }

    private ResponseEntity<Object> setObjectResponseEntity(Object resultData, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        ResponseData data = new ResponseData();
        data.setStatus(StatusEnum.OK);
        data.setMessage(message);
        data.setData(resultData);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
