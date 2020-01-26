package com.shifthunter.tasks;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "binario-task")
public class Task1Properties {
	

	/**
	 * The name of the Operation
	 */
	private String operationName;
	
	
	/**
	 * The Test Case Line to Convert into Json
	 */
	private String testCaseLine;
	
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getTestCaseLine() {
		return testCaseLine;
	}

	public void setTestCaseLine(String testCaseLine) {
		this.testCaseLine = testCaseLine;
	}

	
}
