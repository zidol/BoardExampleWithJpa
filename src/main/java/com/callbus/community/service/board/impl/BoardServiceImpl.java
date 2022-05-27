package com.callbus.community.service.board.impl;

import com.callbus.community.Exception.customException.NotAllowedUserException;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Member;
import com.callbus.community.dto.board.BoardDto;
import com.callbus.community.dto.board.BoardUpdateForm;
import com.callbus.community.dto.board.BoardListDto;
import com.callbus.community.dto.board.BoardSearchForm;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.member.MemberRepository;
import com.callbus.community.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    @Override
    public Page<BoardListDto> getBoardList(BoardSearchForm boardSearchForm, Pageable pageable, Long memberId) throws Exception{
        return boardRepository.getBoardList(boardSearchForm, pageable, memberId);
    }

    @Override
    public Board insertBoard(BoardDto boardDto) throws Exception {
        Member member = memberRepository.findById(boardDto.getMemberId()).orElseThrow(() -> new Exception("존재하지 않는 회원입니다"));
        Board newBoard = Board.builder()
                .subject(boardDto.getSubject())
                .contents(boardDto.getContents())
                .member(member)
                .build();
        return boardRepository.save(newBoard);
    }

    @Override
    @Transactional
    public Board updateBoard(BoardUpdateForm boardForm) throws Exception {
        Board board = boardRepository.findById(boardForm.getId()).orElseThrow(() -> new Exception("찾으신 게시글이 존재하지 않습니다."));

        if (!boardForm.getMemberId().equals(board.getMember().getId())) {
            throw new NotAllowedUserException("작성자만 수정가능합니다");
        }

        if (!board.getIsUse()) {
            throw new Exception("삭제 된 게시글은 수정 불가능합니다");
        }

        board.changeBoard(boardForm);
        return board;
    }

    @Override
    @Transactional
    public Board deleteBoard(BoardUpdateForm boardForm) throws Exception {
        Board board = boardRepository.findById(boardForm.getId()).orElseThrow(() -> new Exception("찾으신 게시글이 존재하지 않습니다."));

        if (!boardForm.getMemberId().equals(board.getMember().getId())) {
            throw new NotAllowedUserException("작성자만 삭제가능합니다");
        }
        board.deleteBoard(boardForm);
        return board;
    }

}
