//package com.shifthunter.tasks;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TaskController {
//	
//	@Autowired
//	private TollProcessingTask t;
//	
//	//pass in POST payload values
//		@RequestMapping(path = "/LinestoJson", method = RequestMethod.POST)
//		public @ResponseBody String launchTask(@RequestBody String request) throws Exception {
//
//			t.run(request.split(","));
//			
//			System.out.println("request made");
//			
//			return "success";
//		}
//}
