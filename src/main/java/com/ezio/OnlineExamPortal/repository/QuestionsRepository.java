package com.ezio.OnlineExamPortal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezio.OnlineExamPortal.entity.Questions;
import com.ezio.OnlineExamPortal.entity.Topic;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {

	List<Questions> findByTopic(Topic topic);
	
	@Query("SELECT count(q) FROM Questions q WHERE q.topic.topicId = :topic_Id")
    Integer getcount(@Param("topic_Id") long topic_Id);

	@Query("SELECT sum(q.marks) FROM Questions q where q.topic.topicId = :topic_Id")
	Integer getSum(@Param("topic_Id") long topic_Id);
	
}
