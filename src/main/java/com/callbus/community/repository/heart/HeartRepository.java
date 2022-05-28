package com.callbus.community.repository.heart;

import com.callbus.community.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 좋아요 repository 영역
 */
@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    /**
     * 중복 검사하기 위한 조회
     *
     * @param memberId
     * @param boardId
     * @return Optional<Heart>
     */
    public Optional<Heart> findByMemberIdAndBoardId(Long memberId, Long boardId);
}
