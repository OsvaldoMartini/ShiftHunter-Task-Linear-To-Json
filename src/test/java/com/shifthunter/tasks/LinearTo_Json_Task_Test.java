package com.shifthunter.tasks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.base.Joiner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LinearTo_Json_Task_Test.TestConfiguration.class })
//@ActiveProfiles("minimal")
@Import(TestBeansConfig.class)
//@EnableConfigurationProperties(Task1Properties.class)
//@EnableTask
public class LinearTo_Json_Task_Test {
	private static final Logger logger = LoggerFactory.getLogger(LinearTo_Json_Task_Test.class);

	@Autowired
	public TollProcessingTask toolP;
	
	@Profile("Linear-To-JsonObject")
	public TollProcessingTask tollProcessingTask() {
		return toolP;
	}

	@Autowired
	public TVertice tVertice;
	
	@Autowired
    private Task1Properties config;
	
//	@Bean
//	public Task1Properties config() {
//		return new Task1Properties();
//	};

	@Test
	public void taskProcessorTest() {
		String columnRequest = Joiner.on(",").skipNulls().join(
				"testCaseLine=Test_2",
				"operationName=Martini");

		// taskP.publishRequest("D:/Projects/X-DynamicGenerator-SoapUI/DataTypeGen-Files,D:/Projects/X-DynamicGenerator-SoapUI/DataTypeGen-Files,D:/Projects/PST2635-COP1-ConvenienceCardWebSite2020,Swagger,Martini");
		// taskP.publishRequest(columnRequest, "Martini");

		//TollProcessingTask toolP = new TollProcessingTask();
		//toolP.config = config();

		try {
			toolP.run(columnRequest.split(","));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assert false;
		}

		logger.info("request made");
	}
	
	@EnableConfigurationProperties(Task1Properties.class)
    public static class TestConfiguration {
        // nothing
    }
}