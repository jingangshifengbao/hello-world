package com.example.jdbeancheckin.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "jd.checkin")
public class JdCheckinProperties {

	/**
	 * Master switch for scheduled check-in.
	 */
	private boolean enabled = true;

	/**
	 * Use dry-run mode to verify account loading and scheduling without calling JD.
	 */
	private boolean dryRun = true;

	/**
	 * Cron expression for automatic check-in. Default: every day at 09:10 Beijing time.
	 */
	private String cron = "0 10 9 * * *";

	/**
	 * Time zone used by the cron expression.
	 */
	private String zone = "Asia/Shanghai";

	/**
	 * Timeout for JD HTTP requests.
	 */
	private Duration requestTimeout = Duration.ofSeconds(15);

	/**
	 * JD sign-in API endpoint. JD may change endpoints, so this is configurable.
	 */
	@NotBlank
	private String endpoint = "https://api.m.jd.com/client.action";

	/**
	 * JD functionId for bean sign-in. Keep configurable because JD can change it.
	 */
	@NotBlank
	private String functionId = "signBeanIndex";

	/**
	 * App id sent to JD API.
	 */
	@NotBlank
	private String appId = "ld";

	/**
	 * Request body sent to JD API.
	 */
	@NotBlank
	private String body = "{\"fp\":\"-1\",\"shshshfp\":\"-1\",\"shshshfpa\":\"-1\",\"referUrl\":\"-1\",\"userAgent\":\"-1\",\"jda\":\"-1\",\"rnVersion\":\"3.9\"}";

	@Valid
	@NotEmpty(message = "Configure at least one JD account")
	private List<Account> accounts = new ArrayList<>();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public void setDryRun(boolean dryRun) {
		this.dryRun = dryRun;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public Duration getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(Duration requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public static class Account {
		/**
		 * Display name used in logs and API responses.
		 */
		@NotBlank
		private String name;

		/**
		 * JD web cookie. Prefer injecting this through environment variables or a secret manager.
		 */
		@NotBlank
		private String cookie;

		private boolean enabled = true;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCookie() {
			return cookie;
		}

		public void setCookie(String cookie) {
			this.cookie = cookie;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
	}
}
