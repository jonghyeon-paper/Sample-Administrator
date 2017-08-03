package com.skplanet.iba.scheduler;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailJob {

	/**
	 * 매일 오전 0시 10분에 실행
	 */
	@Scheduled(cron = "0 10 0 * * *")
	public void print() {
		System.out.println(" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% >>> " + new Date());
	}
}
