package com.ezio.OnlineExamPortal.UserRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;


@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistrationEntity, Long> {

	UserRegistrationEntity findByEmailAndPassword(String email, String password);

	UserRegistrationEntity findByEmail(String email);

	long countByRegistrationDate(LocalDate registrationDate);
	
	
}
