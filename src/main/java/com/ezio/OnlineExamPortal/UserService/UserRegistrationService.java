package com.ezio.OnlineExamPortal.UserService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserRepository.UserRegistrationRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserRegistrationService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	public UserRegistrationEntity saveRegistration(UserRegistrationEntity userRegistrationEntity) {
		return userRegistrationRepository.save(userRegistrationEntity);
	}
	
	public UserRegistrationEntity login(String email, String password) {
		UserRegistrationEntity userRegistrationEntity =  userRegistrationRepository.findByEmailAndPassword(email, password);
		 return userRegistrationEntity;
		}
	
	 public boolean emailExists(String email) {
	        return userRegistrationRepository.findByEmail(email) != null;
	    }
	 
	 public List<UserRegistrationEntity> getUser(){
		 return userRegistrationRepository.findAll();
	 }


	 public long getNewUsersCount() {
	        LocalDate today = LocalDate.now();
	        return userRegistrationRepository.countByRegistrationDate(today);
	    }

	 public UserRegistrationEntity getUserId(Long id) {
		 return userRegistrationRepository.findById(id).orElse(null);
	 }
	 
	 
	 public UserRegistrationEntity editUser(Long id, String firstName, String lastName, String password, String confirmPassword, String mobileNum, String email) {
		    if (id == null) {
		        throw new IllegalArgumentException("The given id must not be null");
		    }
		    
		    UserRegistrationEntity existingUserRegistrationEntity = userRegistrationRepository.findById(id)
		        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		    
		    existingUserRegistrationEntity.setFirstName(firstName);
		    existingUserRegistrationEntity.setLastName(lastName);
		    existingUserRegistrationEntity.setPassword(password);
		    existingUserRegistrationEntity.setConfirmPassword(confirmPassword);
		    existingUserRegistrationEntity.setMobileNum(mobileNum);
		    existingUserRegistrationEntity.setEmail(email);
		   // existingUserRegistrationEntity.setRegistrationDate(userRegistrationEntity.getRegistrationDate());
		    
		    return userRegistrationRepository.save(existingUserRegistrationEntity);
		}

	 
		/*
		 * public UserRegistrationEntity saveLogin(UserRegistrationEntity
		 * userRegistrationEntity) {
		 * 
		 * return null; }
		 */
}
