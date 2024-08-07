package com.ezio.OnlineExamPortal.UserController;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezio.OnlineExamPortal.UserEntity.ExamResults;
import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserRepository.ExamResultRepository;
import com.ezio.OnlineExamPortal.UserRepository.UserRegistrationRepository;
import com.ezio.OnlineExamPortal.UserService.EmailService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ExamResultRepository examResultRepository;
	
	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
//	public EmailController(EmailService emailService) {
//		this.emailService = emailService;
//	}
	
	
	 @PostMapping("/send")
	 @ResponseBody
	    public String sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file, 
	                           @RequestParam String to, 
	                           @RequestParam(required = false) String[] cc, 
	                           @RequestParam String subject, 
	                           @RequestParam String body) {
	        return emailService.sendMail(file, to, cc, subject, body);
	    }

	 @PostMapping("/sendOtp")
	 @ResponseBody
	    public ResponseEntity<String> sendOtp(@RequestParam("email") String email) {
	        try {
	            emailService.sendOtp(email);
	            return ResponseEntity.ok("OTP sent successfully");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Failed to send OTP");
	        }
	    }
	
	 @PostMapping("/verifyOtp")
	 @ResponseBody
	    public ResponseEntity<String> verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
	        boolean isOtpValid = emailService.verifyOtp(email, otp);
	        if (isOtpValid) {
	            return ResponseEntity.ok("OTP verified successfully");
	        } else {
	            return ResponseEntity.status(400).body("Invalid OTP");
	        }
	    }
	 
	 
	 @PostMapping("/changePassword")
	 @ResponseBody
	 public ResponseEntity<String> changePassword(
	         @RequestParam("email") String email, 
	         @RequestParam("newPassword") String newPassword, 
	         @RequestParam("confirmPassword") String confirmPassword) {
	     try {
	         emailService.changePassword(email, newPassword, confirmPassword);
	         return ResponseEntity.ok("Password changed successfully");
	     } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(500).body("Failed to change password");
	     }
	 }



	
}
