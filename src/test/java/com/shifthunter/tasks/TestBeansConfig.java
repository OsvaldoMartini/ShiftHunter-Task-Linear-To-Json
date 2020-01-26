package com.shifthunter.tasks;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
//@EnableConfigurationProperties(Task1Properties.class)
public class TestBeansConfig {
	
	@Bean
	public TollProcessingTask tollProcessingTask() {
		return new TollProcessingTask();
	}

	@Bean
	public TVertice tVertice() {
		return new TVertice();
	}
	
//	@Bean
//	public Task1Properties task1Properties() {
//		return new Task1Properties();
//	}

}