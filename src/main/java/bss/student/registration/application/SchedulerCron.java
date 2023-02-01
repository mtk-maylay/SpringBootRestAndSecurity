package bss.student.registration.application;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerCron {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Scheduled(cron = "0 0 0/1 1/1 * ?")
	public void task() {
		System.out.println("Scheduler (cron expression) task with duration : " + sdf.format(new Date()));
	}
}
