package com.mtakworld.mvc.servlet.basic.domain.member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MemberRepositoryTest {

	MemberRepository memberRepository = MemberRepository.getInstance();

	@AfterEach
	void afterEach() {
		memberRepository.clear();
	}
	@Test
	void getInstance() {
		Member mtak = new Member("mtak", 26);
		Member saved = memberRepository.save(mtak);
		Member findMember = memberRepository.findById(saved.getId());
		assertThat(mtak).isEqualTo(saved);
	}

	@Test
	void save() {
		Member mtak = new Member("mtak", 26);
		Member yeji = new Member("yeji", 24);
		memberRepository.save(mtak);
		memberRepository.save(yeji);
		List<Member> all = memberRepository.findAll();

		assertThat(all.size()).isEqualTo(2);
		assertThat(all).contains(mtak, yeji);
	}

	@Test
	void findById() {
	}

	@Test
	void clear() {
	}
}