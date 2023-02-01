package bss.student.registration.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "bss.student.registration" })
@EnableScheduling
public class StudentApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudentApplication.class, args);
	}

}
