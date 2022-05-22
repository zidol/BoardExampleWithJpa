package com.callbus.community;

import com.callbus.community.domain.Member;
import com.callbus.community.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommunityApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	@DisplayName("QueryDsl 세팅 확인 테스트")
	void contextLoads() {
		Member member = Member.builder()
				.nickname("zidol")
				.build();
		em.persist(member);

		JPAQueryFactory query = new JPAQueryFactory(em);

		QMember qMember = new QMember("m");

		Member result = query.selectFrom(qMember).fetchOne();

		assertThat(result).isEqualTo(member);
		assertThat(result.getId()).isEqualTo(member.getId());

	}

}
