package com.ezio.OnlineExamPortal.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Questions {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="queId")
	    private Long queId;

	 	private Integer queNum;
	 	
	    private String questions;
	    private String option1;
	    private String option2;
	    private String option3;
	    private String option4;
	    private String answer;
	    private Integer marks;
	    
	    
	    @Lob
	    @Column(name = "image_data", columnDefinition = "LONGBLOB")
	    private byte[] imageData;
	    
	    
	    @ManyToOne
	    @JoinColumn(name = "topic_Id")
	    @JsonBackReference
	    private Topic topic;


	    
		public Questions() {
			super();
			// TODO Auto-generated constructor stub
		}


		public Questions(Long queId, Integer queNum, String questions, String option1, String option2, String option3,
				String option4, String answer, Integer marks, Topic topic) {
			super();
			this.queId = queId;
			this.queNum = queNum;
			this.questions = questions;
			this.option1 = option1;
			this.option2 = option2;
			this.option3 = option3;
			this.option4 = option4;
			this.answer = answer;
			this.marks = marks;
			this.topic = topic;
		}

		public byte[] getImageData() {
	        return imageData;
	    }

	    public void setImageData(byte[] imageData) {
	        this.imageData = imageData;
	    }

		public Integer getMarks() {
			return marks;
		}


		public void setMarks(Integer marks) {
			this.marks = marks;
		}


		public Long getQueId() {
			return queId;
		}



		public void setQueId(Long queId) {
			this.queId = queId;
		}



		public Integer getQueNum() {
			return queNum;
		}



		public void setQueNum(Integer queNum) {
			this.queNum = queNum;
		}



		public String getQuestions() {
			return questions;
		}



		public void setQuestions(String questions) {
			this.questions = questions;
		}



		public String getOption1() {
			return option1;
		}



		public void setOption1(String option1) {
			this.option1 = option1;
		}



		public String getOption2() {
			return option2;
		}



		public void setOption2(String option2) {
			this.option2 = option2;
		}



		public String getOption3() {
			return option3;
		}



		public void setOption3(String option3) {
			this.option3 = option3;
		}



		public String getOption4() {
			return option4;
		}



		public void setOption4(String option4) {
			this.option4 = option4;
		}



		public String getAnswer() {
			return answer;
		}



		public void setAnswer(String answer) {
			this.answer = answer;
		}



		public Topic getTopic() {
			return topic;
		}



		public void setTopic(Topic topic) {
			this.topic = topic;
		}



		@Override
		public String toString() {
			return "Questions [queId=" + queId + ", queNum=" + queNum + ", questions=" + questions + ", option1="
					+ option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", answer="
					+ answer + ", topic=" + topic + "]";
		}



		
}
