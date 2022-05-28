package com.callbus.community.api;

import com.callbus.community.domain.Board;
import com.callbus.community.dto.board.BoardDto;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.dto.common.MemberInfo;
import com.callbus.community.service.board.BoardService;
import com.callbus.community.util.common.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 게시판 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class BoardController {

    private final BoardService boardService;

//    private final CommonUtils utils;

    /**
     * 게시판 목록 조회 api
     * @param boardSearchForm
     * @param pageable
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/boards")
    public ResponseEntity<Object> getBoards(BoardSearchForm boardSearchForm
            , Pageable pageable, HttpServletRequest request) throws Exception {

        log.info("게시판 목록 조회");
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        Page<BoardListDto> boardList = boardService.getBoardList(boardSearchForm, pageable, memberInfo == null ? null : memberInfo.getMemberId());

        return CommonUtils.setObjectResponseEntity(boardList, "정상적으로 조회 하셨습니다");
    }

    /**
     * 게시글 등록 api
     *
     * @param boardDto
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/boards")
    public ResponseEntity<Object> insertBoard(@RequestBody @Valid BoardDto boardDto, HttpServletRequest request) throws Exception {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        System.out.println("memberInfo.getId() = " + memberInfo.getMemberId());
        boardDto.setMemberId(memberInfo.getMemberId());

        Board insertBoard = boardService.insertBoard(boardDto);

        return CommonUtils.setObjectResponseEntity(null, "정상적으로 등록 하셨습니다");
    }

    /**
     * 게시글 수정 api
     *
     * @param id
     * @param boardForm
     * @return
     * @throws Exception
     */
    @PutMapping("/boards/{id}")
    public ResponseEntity<Object> updateBoard(@PathVariable("id") Long id,
                                              @RequestBody @Valid BoardUpdateForm boardForm, HttpServletRequest request) throws Exception {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");
        boardForm.setId(id);
        boardForm.setMemberId(memberInfo.getMemberId());
        boardService.updateBoard(boardForm);

        return CommonUtils.setObjectResponseEntity(null, "정상적으로 수정 하였습니다.");

    }

    /**
     * 게시글 삭제 api
     *
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<Object> deleteBoard(@PathVariable("id") Long id, HttpServletRequest request) throws Exception {
        MemberInfo memberInfo = (MemberInfo) request.getAttribute("memberInfo");

        BoardUpdateForm boardForm = new BoardUpdateForm();
        boardForm.setMemberId(memberInfo.getMemberId());
        boardForm.setId(id);
        boardForm.setIsUse(false);

        boardService.deleteBoard(boardForm);

        return CommonUtils.setObjectResponseEntity(null, "정상적으로 삭제 하였습니다.");

    }

}
