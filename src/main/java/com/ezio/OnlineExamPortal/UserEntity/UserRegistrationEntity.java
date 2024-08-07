package com.ezio.OnlineExamPortal.UserEntity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "userRegistrationEntity")
public class UserRegistrationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id") 
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNum;
	private String password;
	private String confirmPassword;
	private LocalDate registrationDate;
	
	@OneToMany(mappedBy = "userRegistrationEntity",  cascade = CascadeType.ALL)
	@JsonBackReference
	private List<ExamResults> examResults;
	
	
	
	
	public List<ExamResults> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<ExamResults> examResults) {
		this.examResults = examResults;
	}

	public UserRegistrationEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UserRegistrationEntity(Long id, String firstName, String lastName, String email, String mobileNum,
			String password, String confirmPassword, LocalDate registrationDate, List<ExamResults> examResults) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNum = mobileNum;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.registrationDate = registrationDate;
		this.examResults = examResults;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobNum) {
		this.mobileNum = mobNum;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}


	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	
	
}
