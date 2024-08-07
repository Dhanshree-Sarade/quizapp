package com.ezio.OnlineExamPortal.EmailImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserRepository.UserRegistrationRepository;
import com.ezio.OnlineExamPortal.UserService.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	 @Autowired
	 private UserRegistrationRepository userRegistrationRepository;
	
	 // Store OTPs temporarily
    private Map<String, String> otpStorage = new HashMap<>();

	@Override
	public String sendMail(MultipartFile[] files, String to, String[] cc, String subject, String body) {
		 try {
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	            helper.setTo(to);
	            if (cc != null && cc.length > 0) {
	                helper.setCc(cc);
	            }
	            helper.setSubject(subject);
	            helper.setText(body, true);

	            if (files != null && files.length > 0) {
	                for (MultipartFile file : files) {
	                    helper.addAttachment(file.getOriginalFilename(), file);
	                }
	            }

	            javaMailSender.send(mimeMessage);
	            return "Mail sent successfully";
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            return "Error sending email: " + e.getMessage();
	        }
	    }

	    public void sendSimpleEmail(String to, String subject, String body) {
	        try {
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(body, true);

	            javaMailSender.send(mimeMessage);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    @Override
	    public void sendOtp(String email) {
	        String otp = generateOtp();
	        otpStorage.put(email, otp); // Store OTP associated with email
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(email);
	        message.setSubject("Your OTP Code");
	        message.setText("Your OTP code is: " + otp);
	        javaMailSender.send(message);
	    }

	    private String generateOtp() {
	        Random random = new Random();
	        int otp = random.nextInt(999999);
	        return String.format("%06d", otp); // Format to 6 digits
	    }

		@Override
		 public boolean verifyOtp(String email, String otp) {
	        String storedOtp = otpStorage.get(email);
	        return otp != null && otp.equals(storedOtp);
	    }
		
		
		public void changePassword(String email, String newPassword, String confirmPassword) {
		    UserRegistrationEntity user = userRegistrationRepository.findByEmail(email);
		    if (user == null) {
		        throw new RuntimeException("User not found");
		    }

		    if (!newPassword.equals(confirmPassword)) {
		        throw new RuntimeException("Passwords do not match");
		    }

		    // Update user's password and confirmPassword
		    user.setPassword(newPassword); // Consider hashing the password
		    user.setConfirmPassword(confirmPassword); // Update confirmPassword
		    userRegistrationRepository.save(user);
		}



	    
}