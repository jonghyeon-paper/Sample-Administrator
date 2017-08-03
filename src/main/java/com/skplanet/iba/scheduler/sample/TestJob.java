package com.skplanet.iba.scheduler.sample;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestJob {

	@Scheduled(fixedDelay = 10000)
	public void print() {
		System.out.println(" @#$@#$@#$@$#@#$@#$@#$@#$@#$@#$@#$@#$# >>> " + new Date());
	}
}
