package com.shifthunter.tasks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansDefinition {
	
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
