package com.ezio.OnlineExamPortal.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.repository.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	public Topic addCategory(Topic topic) {
	    // Convert duration string to LocalTime
	    System.err.println("service" + topic.getTopicName());
	    String durationString = topic.getDurationString(); // Get the duration string
	    
	    // Check if durationString is null or empty
	    if (durationString == null || durationString.isEmpty()) {
	        throw new IllegalArgumentException("Duration string is null or empty.");
	    }
	    
	    try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	        LocalTime duration = LocalTime.parse(durationString, formatter);
	        topic.setDuration(duration);
	    } catch (DateTimeParseException e) {
	        // Handle the exception
	        e.printStackTrace();
	        throw new IllegalArgumentException("Invalid duration format. Please use HH:mm:ss format.");
	    }
	    return topicRepository.save(topic);
	}

	
	public List<Topic> getCategory(){
		return topicRepository.findAll();
	}

	public Topic getTopicById(Long topicId) {
		// TODO Auto-generated method stub
		return topicRepository.findById(topicId).orElse(null);
	}
	
//	public Topic editDetails(Topic topic) {
//		Topic existingTopic = topicRepository.findById(topic.getTopicId()).orElseThrow();
//		if (existingTopic!=null) {
//			existingTopic.setTopicName(topic.getTopicName());
//			existingTopic.setDescription(topic.getDescription());
//			existingTopic.setLanguage(topic.getLanguage());
//			if (topic.getDurationString() != null && !topic.getDurationString().isEmpty()) {
//	            existingTopic.setDuration(LocalTime.parse(topic.getDurationString()));
//	        }
//			return topicRepository.save(existingTopic);
//		}
//		return null;
//	}
	
	public Topic editDetails(Topic topic) {
		Topic existingTopic = topicRepository.findById(topic.getTopicId()).orElseThrow();
		System.err.println("service"+topic.getTopicId());
		if (existingTopic != null) {
			existingTopic.setTopicName(topic.getTopicName());
			existingTopic.setInstituteName(topic.getInstituteName());
			existingTopic.setDescription(topic.getDescription());
			existingTopic.setLanguage(topic.getLanguage());
			existingTopic.setStatus(topic.isStatus());

			if (topic.getDurationString() != null && !topic.getDurationString().isEmpty()) {
				existingTopic.setDuration(LocalTime.parse(topic.getDurationString()));
			}

		}
		return topicRepository.save(existingTopic);
	}

	
	
	public String deleteRecord(Long topicId) {
		topicRepository.deleteById(topicId);
		return "Record deleted successfully...";
	}
	
	
//	public List<Topic> fetchUsingTopicId(Long topicId) {
//		System.out.println(topicId);
//		return topicRepository.findByDescriptionTopicName(topicId);
//	}
	
//	public void updateUploadStatus(Long topicId, boolean uploaded) {
//        Optional<Topic> optionalTopic = topicRepository.findById(topicId);
//        if (optionalTopic.isPresent()) {
//            Topic topic = optionalTopic.get();
//            topic.setStatus(uploaded);
//            topicRepository.save(topic);
//        }
//    }
}
