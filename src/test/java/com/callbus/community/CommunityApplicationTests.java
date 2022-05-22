package com.callbus.community;

import com.callbus.community.domain.Member;
import com.callbus.community.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
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
	void contextLoads() {
		Member member = new Member();
		member.setName("tester");
		em.persist(member);


		JPAQueryFactory query = new JPAQueryFactory(em);

		QMember qMember = new QMember("m");

		Member result = query.selectFrom(qMember).fetchOne();

		assertThat(result).isEqualTo(member);
		assertThat(result.getId()).isEqualTo(member.getId());

	}

}
