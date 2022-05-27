package com.callbus.community.repository.heart;

import com.callbus.community.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    public Optional<Heart> findByMemberIdAndBoardId(Long memberId, Long boardId);
}
