package com.example.jdbeancheckin.controller;

import com.example.jdbeancheckin.model.CheckinResult;
import com.example.jdbeancheckin.service.JdCheckinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckinController {

	private final JdCheckinService checkinService;

	public CheckinController(JdCheckinService checkinService) {
		this.checkinService = checkinService;
	}

	@PostMapping("/jd-beans")
	public List<CheckinResult> checkinJdBeans() {
		return checkinService.checkinAll();
	}
}
