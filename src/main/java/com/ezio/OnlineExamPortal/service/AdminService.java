package com.ezio.OnlineExamPortal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezio.OnlineExamPortal.entity.Admin;
import com.ezio.OnlineExamPortal.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	
	public Admin login(String username, String password) {
		Admin admin =  adminRepository.findByUsernameAndPassword(username, password);
		 return admin;
		}

	}
