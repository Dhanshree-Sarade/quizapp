package com.ezio.OnlineExamPortal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.repository.TopicRepository;
import com.ezio.OnlineExamPortal.service.TopicService;

@Controller
public class TopicController {
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private TopicRepository topicRepository;
	
//	@PostMapping("/addCategory")
//	@ResponseBody
//	public Topic addCategory(@RequestBody Topic topic) {
//		return topicService.addCategory(topic);
//	}
	
	@PostMapping("/addCategory")
	@ResponseBody
	public Topic addCategory(@RequestParam("topicName") String topicName,
	                         @RequestParam("description") String description, 
	                         @RequestParam("language") String language,
	                         @RequestParam("status") boolean status, 
	                         @RequestParam("durationString") String durationString,
	                         @RequestParam("instituteName") String instituteName) {
	    // Validate required parameters
	    System.out.println(topicName + description + language + status + durationString);
	    if (topicName == null || description == null || language == null || durationString == null) {
	        throw new IllegalArgumentException("One or more required parameters are missing.");
	    }

	    // Create a new Topic object
	    Topic topic = new Topic();
	    topic.setTopicName(topicName);
	    topic.setDescription(description);
	    topic.setLanguage(language);
	    topic.setStatus(status);
	    topic.setDurationString(durationString);
	    topic.setInstituteName(instituteName);

	    System.out.println("yuguhhiuh" + topic.getTopicName());

	    // Call the service method to save the topic
	    Topic savedTopic = topicService.addCategory(topic);
	    System.err.println(savedTopic);
	    // Return the saved topic (optional, depending on your needs)
	    return savedTopic;
	}



	
	@GetMapping("/getCategory")
	@ResponseBody
	public List<Topic> showCategory(){
		return topicService.getCategory();
	}
	
	@GetMapping("/topicById/{topicId}")
	@ResponseBody
    public Topic getTopicById(@PathVariable Long topicId) {
		return topicService.getTopicById(topicId);
    }
	
//	@RequestMapping("/editDetails")
//	@ResponseBody
//	public Topic updatedetails(@RequestBody Topic topic) {
//		return topicService.editDetails(topic);
//	}
	
	@RequestMapping("/editDetails")
	@ResponseBody
	public Topic updateDetails(@RequestParam("topicId") Long topicId, @RequestParam("topicName") String topicName,
			@RequestParam("description") String description, @RequestParam("language") String language,
			@RequestParam(value = "status",required = false) boolean status,
			@RequestParam(value = "durationString", required = false) String durationString, @RequestParam("instituteName") String instituteName) {

		System.out.println("Received parameters: " + topicId + ", " + topicName + ", " + description + ", " + language
				+ ", " + status + ", " + durationString);

		Topic topic = new Topic();
		topic.setTopicId(topicId);
		topic.setInstituteName(instituteName);
		topic.setTopicName(topicName);
		topic.setDescription(description);
		topic.setLanguage(language);
		topic.setStatus(status);
		topic.setDurationString(durationString);

		return topicService.editDetails(topic);
	}



	
	@RequestMapping("/deleteId/{topicId}")
	@ResponseBody
	public String removeData(@PathVariable Long topicId) {
		return topicService.deleteRecord(topicId);
	}
	
	
//	@GetMapping("/fetchUsingTopicId/{topicId}")
//	@ResponseBody
//	public List<Topic> fetchTopicId(@PathVariable Long topicId) {
//		return topicService.fetchUsingTopicId(topicId);
//	}
	
}
