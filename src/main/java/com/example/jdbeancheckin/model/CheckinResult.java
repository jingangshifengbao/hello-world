package com.example.jdbeancheckin.model;

import java.time.Instant;

public class CheckinResult {

	private final String accountName;
	private final boolean success;
	private final int httpStatus;
	private final String message;
	private final String responseBody;
	private final Instant checkedAt;

	public CheckinResult(String accountName, boolean success, int httpStatus, String message, String responseBody, Instant checkedAt) {
		this.accountName = accountName;
		this.success = success;
		this.httpStatus = httpStatus;
		this.message = message;
		this.responseBody = responseBody;
		this.checkedAt = checkedAt;
	}

	public String getAccountName() {
		return accountName;
	}

	public boolean isSuccess() {
		return success;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public Instant getCheckedAt() {
		return checkedAt;
	}
}