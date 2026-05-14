package com.example.jdbeancheckin.service;

import com.example.jdbeancheckin.config.JdCheckinProperties;
import com.example.jdbeancheckin.model.CheckinResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdCheckinScheduler {

	private static final Logger log = LoggerFactory.getLogger(JdCheckinScheduler.class);

	private final JdCheckinProperties properties;
	private final JdCheckinService checkinService;

	public JdCheckinScheduler(JdCheckinProperties properties, JdCheckinService checkinService) {
		this.properties = properties;
		this.checkinService = checkinService;
	}

	@Scheduled(cron = "${jd.checkin.cron}", zone = "${jd.checkin.zone}")
	public void runScheduledCheckin() {
		if (!properties.isEnabled()) {
			log.debug("JD bean check-in scheduler is disabled");
			return;
		}

		List<CheckinResult> results = checkinService.checkinAll();
		long successCount = results.stream().filter(CheckinResult::isSuccess).count();
		log.info("JD bean check-in completed: {}/{} successful", successCount, results.size());
	}
}
