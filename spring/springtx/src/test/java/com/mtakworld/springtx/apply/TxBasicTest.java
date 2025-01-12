package com.mtakworld.springtx.apply;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class TxBasicTest {
	@Autowired
	BasicService basicService;

	@Test
	void proxyCheck() {
		log.info("aop class={}", basicService.getClass());
		Assertions
			.assertThat(AopUtils.isAopProxy(basicService))
			.isTrue();
	}

	@Test
	void txTest() {
		basicService.tx();
		basicService.nontx();

	}

	@TestConfiguration
	static class TxApplyBasicConfiguration {
		@Bean
		BasicService basicService() {
			return new BasicService();
		}
	}

	static class BasicService {
		@Transactional
		public void tx() {
			log.info("call tx");
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("active is {}", active);
		}

		public void nontx() {
			log.info("call tx");
			boolean active = TransactionSynchronizationManager.isActualTransactionActive();
			log.info("active is {}", active);

		}
	}
}
