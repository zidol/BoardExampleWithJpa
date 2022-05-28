package com.callbus.community.service.heart;

import com.callbus.community.Exception.customException.DuplicateHeartException;
import com.callbus.community.domain.Heart;

import javax.servlet.http.HttpServletRequest;

/**
 * 좋아요 관련 인터페이스
 */
public interface HeartService {

    /**
     * 해당 게시판 좋아요 등록
     * @param memberId
     * @param boardId
     */
    public void insertHeart(Long memberId, Long boardId) throws DuplicateHeartException;
}
