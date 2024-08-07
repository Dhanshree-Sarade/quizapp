package com.ezio.OnlineExamPortal.UserEntity;

import java.time.LocalDateTime;

import com.ezio.OnlineExamPortal.entity.Topic;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_results")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ExamResults {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examResultId")
	private Long id;
	private Integer totalQuestions;
	private Integer correctAnswers;
	private Integer attemptedQuestions;
	private Double percentage;
	private String result;
	private LocalDateTime timestamp;

	private String topicName; // New field for topic name
	private String topicDescription; // New field for topic description

	@ManyToOne
	@JoinColumn(name = "user_id") // Ensure this matches the primary key column name in userRegistrationEntity
	@JsonManagedReference
	private UserRegistrationEntity userRegistrationEntity;

	@ManyToOne
	@JoinColumn(name = "topic_Id")
	// @JsonIgnore
	@JsonManagedReference
	private Topic topic;

	public ExamResults() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamResults(Long id, Integer totalQuestions, Integer correctAnswers, Integer attemptedQuestions,
			Double percentage, String result, LocalDateTime timestamp, String topicName, String topicDescription,
			UserRegistrationEntity userRegistrationEntity, Topic topic) {
		super();
		this.id = id;
		this.totalQuestions = totalQuestions;
		this.correctAnswers = correctAnswers;
		this.attemptedQuestions = attemptedQuestions;
		this.percentage = percentage;
		this.result = result;
		this.timestamp = timestamp;
		this.topicName = topicName;
		this.topicDescription = topicDescription;
		this.userRegistrationEntity = userRegistrationEntity;
		this.topic = topic;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicDescription() {
		return topicDescription;
	}

	public void setTopicDescription(String topicDescription) {
		this.topicDescription = topicDescription;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Integer getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Integer getAttemptedQuestions() {
		return attemptedQuestions;
	}

	public void setAttemptedQuestions(Integer attemptedQuestions) {
		this.attemptedQuestions = attemptedQuestions;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UserRegistrationEntity getUserRegistrationEntity() {
		return userRegistrationEntity;
	}

	public void setUserRegistrationEntity(UserRegistrationEntity userRegistrationEntity) {
		this.userRegistrationEntity = userRegistrationEntity;
	}

	@Override
	public String toString() {
		return "ExamResults [id=" + id + ", totalQuestions=" + totalQuestions + ", correctAnswers=" + correctAnswers
				+ ", attemptedQuestions=" + attemptedQuestions + ", percentage=" + percentage + ", result=" + result
				+ ", userRegistrationEntity=" + userRegistrationEntity + ", topic=" + topic + "]";
	}

}
