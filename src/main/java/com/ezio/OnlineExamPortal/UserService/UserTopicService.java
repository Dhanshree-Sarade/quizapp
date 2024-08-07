package com.ezio.OnlineExamPortal.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.repository.TopicRepository;

@Service
public class UserTopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	public List<Topic> getByLanguage(String language) {
		return topicRepository.findByLanguage(language);
	}
	
	
}
