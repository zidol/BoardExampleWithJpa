package com.callbus.community.service.heart.impl;

import com.callbus.community.Exception.customException.DuplicateHeartException;
import com.callbus.community.Exception.customException.NotFoundException;
import com.callbus.community.domain.Board;
import com.callbus.community.domain.Heart;
import com.callbus.community.domain.Member;
import com.callbus.community.repository.board.BoardRepository;
import com.callbus.community.repository.heart.HeartRepository;
import com.callbus.community.repository.member.MemberRepository;
import com.callbus.community.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 좋아요 관련 인터페이스 구현부
 */
@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertHeart(Long memberId, Long boardId) throws DuplicateHeartException {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("조회 하신 결과가 없습니다."));

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("조회하신 결과가 없습니다."));

        //좋아요 체크
        Optional<Heart> findHeart = heartRepository.findByMemberIdAndBoardId(memberId, boardId);

        if (findHeart.isPresent()) {
            throw new DuplicateHeartException("좋아요를 이미 하셨습니다.");
        } else {
            Heart heart = Heart.builder()
                    .member(member)
                    .board(board).build();
            heartRepository.save(heart);
        }

    }
}
