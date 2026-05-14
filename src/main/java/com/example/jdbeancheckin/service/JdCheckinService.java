package com.example.jdbeancheckin.service;

import com.example.jdbeancheckin.config.JdCheckinProperties;
import com.example.jdbeancheckin.model.CheckinResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@Service
public class JdCheckinService {

	private static final Logger log = LoggerFactory.getLogger(JdCheckinService.class);

	private final JdCheckinProperties properties;
	private final RestTemplate restTemplate;

	public JdCheckinService(JdCheckinProperties properties, RestTemplateBuilder restTemplateBuilder) {
		this.properties = properties;
		this.restTemplate = restTemplateBuilder
				.connectTimeout(properties.getRequestTimeout())
				.readTimeout(properties.getRequestTimeout())
				.build();
	}

	public List<CheckinResult> checkinAll() {
		return properties.getAccounts().stream()
				.filter(JdCheckinProperties.Account::isEnabled)
				.map(this::checkin)
				.toList();
	}

	public CheckinResult checkin(JdCheckinProperties.Account account) {
		if (properties.isDryRun()) {
			log.info("Dry-run JD bean check-in for account {}", account.getName());
			return new CheckinResult(
					account.getName(),
					true,
					0,
					"dry-run: account configuration loaded; JD endpoint was not called",
					null,
					Instant.now()
			);
		}

		URI uri = UriComponentsBuilder.fromUriString(properties.getEndpoint())
				.queryParam("functionId", properties.getFunctionId())
				.queryParam("appid", properties.getAppId())
				.queryParam("body", properties.getBody())
				.build()
				.encode()
				.toUri();

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.COOKIE, account.getCookie());
		headers.set(HttpHeaders.USER_AGENT, "jd-bean-checkin/0.0.1");
		headers.set(HttpHeaders.REFERER, "https://bean.m.jd.com/");

		try {
			ResponseEntity<String> response = restTemplate.exchange(
					uri,
					HttpMethod.GET,
					new HttpEntity<>(headers),
					String.class
			);
			boolean success = response.getStatusCode().is2xxSuccessful();
			return new CheckinResult(
					account.getName(),
					success,
					response.getStatusCode().value(),
					success ? "JD endpoint returned a 2xx response" : "JD endpoint returned a non-2xx response",
					response.getBody(),
					Instant.now()
			);
		} catch (RestClientException ex) {
			log.warn("JD bean check-in failed for account {}: {}", account.getName(), ex.getMessage());
			return new CheckinResult(
					account.getName(),
					false,
					0,
					ex.getMessage(),
					null,
					Instant.now()
			);
		}
	}
}
