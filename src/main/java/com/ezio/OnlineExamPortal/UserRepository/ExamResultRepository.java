package com.ezio.OnlineExamPortal.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezio.OnlineExamPortal.UserEntity.ExamResults;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResults, Long> {

	ExamResults findTopByUserRegistrationEntityIdOrderByTimestampDesc(Integer id);
	
	List<ExamResults> findByUserRegistrationEntityId(Long userId);
	
//	@Query("SELECT er FROM ExamResults er WHERE er.userRegistrationEntity.id = :userId AND er.topic.topicId = :topicId")
//    List<ExamResults> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);
	
	@Query("SELECT er FROM ExamResults er WHERE er.userRegistrationEntity.id = :userId AND er.topic.topicId = :topicId")
    List<ExamResults> findByUserIdAndTopicId(@Param("userId") Long userId, @Param("topicId") Long topicId);


    @Query("SELECT e FROM ExamResults e JOIN FETCH e.userRegistrationEntity u JOIN FETCH e.topic t WHERE e.id = :id")
    Optional<ExamResults> findByIdWithDetails(@Param("id") Long id);
    
    @Query("SELECT e FROM ExamResults e JOIN FETCH e.userRegistrationEntity")
    List<ExamResults> findAllWithUserDetails();
	
}
