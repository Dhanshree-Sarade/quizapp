package com.ezio.OnlineExamPortal.entity;

import java.time.LocalTime;
import java.util.List;

import com.ezio.OnlineExamPortal.UserEntity.ExamResults;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="topic")
public class Topic {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    private String topicName;
    private String description;
    private String language;
    private boolean status;
    private LocalTime duration; 
    private String instituteName;
    
    @Transient
    private String durationString; // Transient field for receiving the duration as string

    
    
   	public Topic() {
   		super();
   		// TODO Auto-generated constructor stub
   	}



	public Topic(Long topicId) {
		// TODO Auto-generated constructor stub
		 this.topicId = topicId;
	}



	@OneToMany(mappedBy = "topic" , cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Questions> questions;

    
    @OneToMany(mappedBy = "topic")
    @JsonBackReference
    private List<ExamResults> examResults;



	public Long getTopicId() {
		return topicId;
	}


	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}


	public String getTopicName() {
		return topicName;
	}


	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public LocalTime getDuration() {
		return duration;
	}


	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}


	public String getDurationString() {
		return durationString;
	}


	public void setDurationString(String durationString) {
		this.durationString = durationString;
	}


	public List<Questions> getQuestions() {
		return questions;
	}


	public void setQuestions(List<Questions> questions) {
		this.questions = questions;
	}


	public List<ExamResults> getExamResults() {
		return examResults;
	}


	public void setExamResults(List<ExamResults> examResults) {
		this.examResults = examResults;
	}


	public String getInstituteName() {
		return instituteName;
	}


	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
    
   
}
