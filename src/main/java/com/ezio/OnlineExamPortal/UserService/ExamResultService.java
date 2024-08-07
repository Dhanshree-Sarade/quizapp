package com.ezio.OnlineExamPortal.UserService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezio.OnlineExamPortal.UserEntity.ExamResults;
import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserRepository.ExamResultRepository;
import com.ezio.OnlineExamPortal.UserRepository.UserRegistrationRepository;
import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.repository.TopicRepository;

@Service
public class ExamResultService {
	
	@Autowired
	private ExamResultRepository examResultRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	public ExamResults saveData(ExamResults examResults) {
		return examResultRepository.save(examResults);
	}
	
	public List<ExamResults> getData(){
		return examResultRepository.findAll();
	}
	
	public ExamResults getDataById(Long id) {
		return examResultRepository.findById(id).orElse(null);
	}

//	public ExamResults save(ExamResults examResults, Long topicId) {
//		return examResultRepository.save(examResults, topicId);
//	}
	
	
	
	public ExamResults saveExamResult(Long topicId, ExamResults examResults) {
	    Optional<Topic> topicOptional = topicRepository.findById(topicId);
	    if (topicOptional.isPresent()) {
	        Topic topic = topicOptional.get();
	        examResults.setTopic(topic);
	        examResults.setTopicName(topic.getTopicName()); // Set topic name
	        examResults.setTopicDescription(topic.getDescription()); // Set topic description
	        return examResultRepository.save(examResults);
	    } else {
	        throw new RuntimeException("Topic not found with id: " + topicId);
	    }
	}

	
	
	public List<ExamResults> getExamResultsByUserId(Long userId) {
        return examResultRepository.findByUserRegistrationEntityId(userId);
    }
	
	
	public List<ExamResults> getExamResultsByUserIdAndTopicId(Long userId, Long topicId) {
        return examResultRepository.findByUserIdAndTopicId(userId, topicId);
    }
	
	 public List<ExamResults> getAllResults() {
	        return examResultRepository.findAllWithUserDetails();
	    }
}
