package com.shifthunter.tasks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LinearTo_Json_Task_Test.TestConfiguration.class })
//@ActiveProfiles("minimal")
@Import(TestBeansConfig.class)
//@EnableConfigurationProperties(Task1Properties.class)
//@EnableTask
public class LinearTo_Json_Task_Test {
	private static final Logger logger = LoggerFactory.getLogger(LinearTo_Json_Task_Test.class);

	private static final ObjectMapper mapper = new ObjectMapper();

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
		String columnRequest = Joiner.on(",").skipNulls().join("testCaseLine=Test_2", "operationName=Martini");

		// taskP.publishRequest("D:/Projects/X-DynamicGenerator-SoapUI/DataTypeGen-Files,D:/Projects/X-DynamicGenerator-SoapUI/DataTypeGen-Files,D:/Projects/PST2635-COP1-ConvenienceCardWebSite2020,Swagger,Martini");
		// taskP.publishRequest(columnRequest, "Martini");

		// TollProcessingTask toolP = new TollProcessingTask();
		// toolP.config = config();

		try {
			toolP.run(columnRequest.split(","));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assert false;
		}

		logger.info("request made");
	}

	@Ignore
	@Test
	public void jsonObjecCreation_Test() throws JsonProcessingException {

		final ObjectNode newNode = mapper.createObjectNode();

		Map<String, Object> mapJson = new HashMap<>();
		
		mapJson.put("firstName", "Harrison");
		mapJson.put("lastName", "Ford");
		mapJson.put("emailAddress", Arrays.asList("harrison@example.com", "hford@actors.com"));

		ArrayNode arrayNode = mapper.createArrayNode();

		ObjectNode game1 = mapper.createObjectNode().objectNode();
		game1.put("name", "Fall Out 4");
		game1.put("price", 49.9);

		ObjectNode game2 = mapper.createObjectNode().objectNode();
		game2.put("name", "Dark Soul 3");
		game2.put("price", 59.9);

		arrayNode.add(game1);
		arrayNode.add(game2);
//      ((ObjectNode) root).set("games", arrayNode);

		ArrayNode arrayNode2 = mapper.createArrayNode();

		ObjectNode game3 = mapper.createObjectNode().objectNode();
		game3.put("firstName", "Osvaldo");
		game3.put("lastName", "Martini");
		arrayNode2.add(game3);

		ObjectNode arrayPlus = mapper.createObjectNode().objectNode();
		arrayPlus.with("Calculation").set("GamesArray", arrayNode);

		ObjectNode arrayPlus2 = mapper.createObjectNode().objectNode();
		arrayPlus2.with("Calculation").set("GamesArray", arrayNode2);

		mapJson.put("Decision", arrayPlus);
		mapJson.put("Decision", arrayPlus2);

		Map<String, Object> mapJson2 = new HashMap<>();
		mapJson2.put("requesdId", "123456");
		mapJson2.put("clientId", "8999999");
		mapJson.put("ObjectRequest", mapJson2);

		JsonNode root2 = mapper.valueToTree(mapJson);
		logger.info(mapper.writeValueAsString(root2));

	}
	
	@EnableConfigurationProperties(Task1Properties.class)
	public static class TestConfiguration {
		// nothing
	}
}