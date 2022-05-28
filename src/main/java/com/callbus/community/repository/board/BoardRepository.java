package com.callbus.community.repository.board;

import com.callbus.community.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @Query("select distinct b from Board b join fetch b.member where b.id = :id and b.isUse = true")
    Optional<Board> findById(@Param("id") Long id);
}
