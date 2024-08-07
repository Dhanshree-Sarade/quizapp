package com.ezio.OnlineExamPortal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezio.OnlineExamPortal.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	 Admin findByUsernameAndPassword(String username, String password); 

}
