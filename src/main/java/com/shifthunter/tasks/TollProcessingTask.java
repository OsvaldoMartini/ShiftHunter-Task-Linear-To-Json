package com.shifthunter.tasks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Joiner;

// Defined Sub Class
//@EnableConfigurationProperties(Task1Properties.class)
@Import(BeansDefinition.class)
//@Configurable
public class TollProcessingTask implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final ObjectMapper mapper = new ObjectMapper();

//	@Autowired
//	public TollProcessingTask toolPT;

	@Value("${pathDataFile}")
	String pathDataFile;

	@Value("${fileExtension}")
	String fileExtension;

	@Value("${environment}")
	String environment;

	@Autowired
	private Task1Properties config;

	@Autowired
	private TVertice tVertice;

	@Override
	public void run(String... strings) throws Exception {

		logger.info("environment : " + environment);
		logger.info("Active Properties");
		logger.info("pathDataFile : " + pathDataFile);
		
		String testCaseLine = System.getProperty("testCaseLine"); 
		String operationName = System.getProperty("operationName"); 
		
		logger.info("Args via \"-DmyVar\" cmd line : ");
		logger.info("testCaseLine : " + testCaseLine);
		logger.info("operationName : " + operationName);
		
		if (null != strings) {

			if (strings.length == 0 && testCaseLine != null &&  operationName != null) {
				strings = Arrays.copyOf(strings, strings.length +1);
				strings[strings.length - 1] = "testCaseLine=" + testCaseLine;
				
				strings = Arrays.copyOf(strings, strings.length +1);
				strings[strings.length - 1] = "operationName=" + operationName;
			}
			
			if (strings.length > 0) {
				
				logger.info("strings.length" + strings.length);

				Map<String, String> argsMap = new HashMap<String, String>();

				for (int x = 0; x < strings.length; x++) {
					String[] arr = strings[x].replace("--", "").split("=");
					argsMap.put(arr[0], arr[1]);
					logger.info("Args " + arr[0] + " " + arr[1]);
				}

				config.setTestCaseLine(argsMap.get("testCaseLine"));
				config.setOperationName(argsMap.get("operationName"));

				logger.info("Arguments \n");
				logger.info("testCaseLine: " + config.getTestCaseLine());
				logger.info("operationName: " + config.getOperationName());
				logger.info("End of Arguments \n");

//				 List<String> args = taskExecution.getArguments().stream()
//					      .map(argument -> (this.argumentSanitizer.sanitize(argument))).collect(Collectors.toList());
//					  taskExecution.setArguments(args);

				String retValue = taskConvertLineToJson(pathDataFile, config.getTestCaseLine(),
						config.getOperationName(), fileExtension);

				System.out.println(retValue);

			}else
			{
				logger.info("Task completed without Params");
				//throw new Exception("Task completed without Params");
			}
		}

	}

	/*
	 * This is To Generate the Data Input <Path Origin File> <Path Destine File
	 * <File Name> Maybe Task Name ???
	 */
	private String taskConvertLineToJson(String pathDataFile, String testCaseLine, String operationName,
			String fileExtension) throws FileNotFoundException, UnsupportedEncodingException {
		// Clean the First HashMap
		TreeMap<String, String[]> lstFieldValue = new TreeMap<>();
		TreeMap<String, String[]> lstFieldAttrib = new TreeMap<>();
		String[] keyAttributes = { ".maxLength", ".minLength", ".required", ".enum" };

		Map<String, String[]> mapLines = readsFile(pathDataFile, testCaseLine, operationName, fileExtension);

		String[] columArray = mapLines.get("ColumnsNames");
		for (String fieldName : columArray) {
			// Last Position in the array
			int lastIndexOf = fieldName.lastIndexOf(".") > -1 ? fieldName.lastIndexOf(".") : 0;
			String lastOne = fieldName.substring(lastIndexOf);
			String allValues = Arrays.asList(keyAttributes).toString();
			allValues = String.join(",", keyAttributes);
		}

		Stack<String> keyPath = new Stack<String>();

		try {

			return identifyObject(mapLines, keyPath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Fail to convert to Json/Soap";


	}

	/*
	 * Read file
	 */
	public Map<String, String[]> readsFile(String pathSource, String testCaseLine, String fileName,
			String fileExtension) {

		String operFileName = pathSource + "/" + fileName + "." + fileExtension;

		try {

			// Definition of first File If Not Exist
			if (!Files.exists(Paths.get(operFileName))) {
				throw new Exception(String.format("File Not Exist: %s : %s", pathSource, fileName));
			}

			Map<String, String[]> mapLines = new HashMap<String, String[]>();

			// it Reads the file
			try (BufferedReader br = new BufferedReader(new FileReader(operFileName))) {
				String line;
				boolean isColumnsNames = true; // For the First Line

				while ((line = br.readLine()) != null) {
					String[] arrColumns = line.split(",");
					if (isColumnsNames) {
						mapLines.put("ColumnsNames", arrColumns);
						isColumnsNames = false;
					} else if (arrColumns[0].equalsIgnoreCase(testCaseLine)) {
						mapLines.put("ColumnsValues", arrColumns);
						logger.info(String.format("Read line: %s ", line));
						break;
					}
				}
				br.close();
			}

			return mapLines;

		} catch (Exception e) {

			logger.info("Error " + e.getMessage());
		}

		return null;
	}

	public ObjectNode addObject(ObjectNode node, Stack<String> key_path, Stack<String> key_value,
			Map<String, Object> mapObject, Map<String, Map<String, Object>> fatherObject)
			throws JsonProcessingException {
		// ObjectNode newNode = mapper.createObjectNode().objectNode();
		// final ObjectNode newNode = mapper.createObjectNode();
		// final ObjectNode newNode = mapper.createObjectNode();

		String value = key_value.pop();
		if (value.indexOf("_#_") == -1) {
			mapObject.put(key_path.pop(), value);
			// ObjectNode newNode2 = addObject(newNode, key_path, key_value);
			node = addObject(node, key_path, key_value, mapObject, fatherObject);

			return node;
			// logger.info(mapper.writeValueAsString(newNode2));

		} else {
			String fatherName = key_path.pop();
			Map<String, Object> mapChilds = fatherObject.get(fatherName);
			if (mapChilds == null) {
				// mapChilds = new Object[]{}; //For Array of Objects
				mapChilds = new HashMap<String, Object>();
				mapChilds.putAll(mapObject);
				// mapChilds = Arrays.copyOf(mapChilds, mapChilds.length +1);
				// mapChilds[mapChilds.length - 1] = mapObject;
				fatherObject.put(fatherName, mapChilds);
			} else {
				// mapChilds = Arrays.copyOf(mapChilds, mapChilds.length +1);
				// mapChilds[mapChilds.length - 1] = mapObject;
				mapChilds.putAll(mapObject);
				fatherObject.put(fatherName, mapChilds);
			}

			// JsonNode node = mapper.valueToTree(fatherObject);
			node = mapper.valueToTree(fatherObject);
			// node.with(key_path.pop(), mapObject);
		}

		// newNode.with(key_path.pop()).set("GamesArray", key_value.pop());

//		for (int x = 0; x < colNames.length; x++) {
//			if (x == colNames.length - 1) {
//				// mapObjects.put(colNames[x], columnValues[i]);
//				// newnewNode.put(colNames[x], "Fall Out 4");
//			} else {
//				// mapObjects.put(colNames[x], "");
//
//			}
//		}

		return node;

		// ObjectNode arrayPlus = mapper.createObjectNode().objectNode();
		// arrayPlus.with("Calculation").set("GamesArray", arrayNode);

		// arrayPlus.with("Calculation").set("GamesArray", arrayNode);

	}

	public String identifyObject(Map<String, String[]> mapLines, Stack<String> keyPath)
			throws JsonProcessingException, IOException {
		// JsonNode root = mapper.readTree("{}");

		String[] columnNames = mapLines.get("ColumnsNames");
		String[] columnValues = mapLines.get("ColumnsValues");
		// Stack<String> keyPath = new Stack<String>();

		// Distinguish the Fields from the Objects
		ObjectNode node = mapper.createObjectNode().objectNode();

		Map<String, String> mapObjects = new HashMap<String, String>();
		Map<String, Map<String, Object>> fatherObject = new HashMap<String, Map<String, Object>>();
		// Map<String, Object[]> fatherObject = new HashMap<String, Object[]>();
		for (int i = 0; i < columnNames.length; i++) {
			if ("DATA_TYPE".equalsIgnoreCase(columnNames[i]) || "TEST_CASE".equalsIgnoreCase(columnNames[i])) {
				continue;
			}

			String[] colNames = columnNames[i].split(Pattern.quote("."));
			Stack<String> key_path = new Stack<String>();
			Stack<String> key_value = new Stack<String>();

			for (int x = 0; x < colNames.length; x++) {
				key_path.push(colNames[x]);
				if (x == colNames.length - 1) {
					mapObjects.put(colNames[x], columnValues[i]);
					key_value.push(columnValues[i]);
				} else {
					mapObjects.put(colNames[x], "");
					key_value.push(x + "_#_");
					// node.put(colNames, "Fall Out 4");

				}

			}
			Map<String, Object> mapObject = new HashMap<String, Object>();
			node = addObject(node, key_path, key_value, mapObject, fatherObject);
			// keyPath.push(colNames);

			// node.put(colNames, "Fall Out 4");

		}

		// If the Dot is the last then the field name is the last

//		try {
//			int idxArr = Integer.parseInt(column);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		String jsonReturn = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		logger.info(jsonReturn);

		return jsonReturn;
	}

//	public JsonNode creationObj(JsonNode root, String fieldName, String fieldValue) {
//		
//		if (fieldValue == "") {
//			creationObj(root, fieldName, fieldValue) {
//		}
//			
//		ObjectNode node = mapper.createObjectNode().objectNode();
//		node.put(fieldName, "Fall Out 4");
//
//		return root;
//		
//	}

	public void displayArray(int[] w) {
		logger.info("\t\t\t[");
		for (int element : w) {
			logger.info(element + " ");
		}
		logger.info("]\n");
	}

	public void displayArray(String[] w) {
		logger.info("\t\t\t[");
		for (String element : w) {
			logger.info(element + " ");
		}
		logger.info("]\n");
	}

	public void display2D(int[][] w) {
		logger.info("\t\t[\n");
		for (int[] w4 : w) {
			logger.info("\t\t\t[");
			for (int element : w4) {
				logger.info(element + " ");
			}
			logger.info("]\n");
		}

	}

}
