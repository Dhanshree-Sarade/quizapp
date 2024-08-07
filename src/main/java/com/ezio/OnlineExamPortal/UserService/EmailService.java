package com.ezio.OnlineExamPortal.UserService;

import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
	
	 String sendMail(MultipartFile[] files, String to, String[] cc, String subject, String body);

		void sendSimpleEmail(String email, String subject, String body);

		void sendOtp(String email);

		boolean verifyOtp(String email, String otp);

		void changePassword(String email, String newPassword, String confirmPassword);
		
		

}
