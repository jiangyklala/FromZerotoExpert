package com.jiang.fzte;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.jiang.fzte.mapper")
public class FzteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FzteApplication.class, args);
	}



}
