package com.shifthunter.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;

//@Import(BeansDefinition.class)
@EnableTask
@SpringBootApplication
@EnableConfigurationProperties(Task1Properties.class)
public class LinearToJsonObjApp {

	public static void main(String[] args) {
		SpringApplication.run(LinearToJsonObjApp.class, args);
		for (String arg : args) {
			System.out.println("Main Entry Args:" + arg);
		}
	}
	
}
