package com.example.jdbeancheckin.model;

import java.time.Instant;

public record CheckinResult(
		String accountName,
		boolean success,
		int httpStatus,
		String message,
		String responseBody,
		Instant checkedAt
) {
}
