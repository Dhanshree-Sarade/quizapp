package com.ezio.OnlineExamPortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezio.OnlineExamPortal.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByLanguage(String language);
	
//	@Query(value = "SELECT description, topic_name FROM Topic where topic_id = :topicId", nativeQuery = true)
//	public List<Topic> findByDescriptionTopicName(@Param("topicId") Long topicId);
//	

}
