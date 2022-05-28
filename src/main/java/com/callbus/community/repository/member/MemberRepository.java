package com.callbus.community.repository.member;

import com.callbus.community.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.id = :id and m.quit = false")
    Optional<Member> findById(@Param("id") Long id);

}
