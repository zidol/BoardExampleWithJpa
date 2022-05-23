package com.callbus.community.domain;

import com.callbus.community.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;


    @Test
    @DisplayName("새회원 생성")
//    @Transactional
    void createMember() {
        Member member = Member.builder()
                .nickname("지돌이")
                .accountId("zidol")
                .accountType(AccountType.LESSEE)
                .build();

        Member save = memberRepository.save(member);

        Member findMember = memberRepository.findById(save.getId()).get();

        assertThat(save.getAccountId()).isEqualTo(findMember.getAccountId());
        assertThat(save.getAccountType().getDesc()).isEqualTo("임차인");
    }
}