package com.example.jdbeancheckin.service;

import com.example.jdbeancheckin.config.JdCheckinProperties;
import com.example.jdbeancheckin.model.CheckinResult;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JdCheckinServiceTest {

	@Test
	void dryRunReturnsResultForEnabledAccountsOnly() {
		JdCheckinProperties properties = new JdCheckinProperties();
		properties.setDryRun(true);

		JdCheckinProperties.Account enabled = new JdCheckinProperties.Account();
		enabled.setName("main");
		enabled.setCookie("pt_key=abc;pt_pin=main;");
		enabled.setEnabled(true);

		JdCheckinProperties.Account disabled = new JdCheckinProperties.Account();
		disabled.setName("backup");
		disabled.setCookie("pt_key=def;pt_pin=backup;");
		disabled.setEnabled(false);

		properties.setAccounts(Lists.newArrayList(enabled, disabled));

		JdCheckinService service = new JdCheckinService(properties, new RestTemplateBuilder());

		List<CheckinResult> results = service.checkinAll();

		assertThat(results).hasSize(1);
		assertThat(results.get(0).accountName()).isEqualTo("main");
		assertThat(results.get(0).success()).isTrue();
		assertThat(results.get(0).message()).contains("dry-run");
	}
}
