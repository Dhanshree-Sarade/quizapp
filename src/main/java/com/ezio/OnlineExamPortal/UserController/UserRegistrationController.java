package com.ezio.OnlineExamPortal.UserController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserService.EmailService;
import com.ezio.OnlineExamPortal.UserService.UserRegistrationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserRegistrationController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRegistrationService userRegistrationService;
	
	
	
	
//	@PostMapping("/saveRegistration")
//	@ResponseBody
//	public ResponseEntity<?> addRegistration(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//		 userRegistrationService.saveRegistration(userRegistrationEntity);
//		 return ResponseEntity.status(HttpStatus.OK).body("{\"redirectUrl\": \"/UserLogin\"}");
//	}
	
	
	
	
	//This is working method for add registration in data base
//	 @PostMapping("/saveRegistration")
//	    @ResponseBody
//	    public ResponseEntity<?> addRegistration(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//	        if (userRegistrationService.emailExists(userRegistrationEntity.getEmail())) {
//	            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
//	        }
//	        
//	        userRegistrationEntity.setRegistrationDate(LocalDate.now());
//
//	        userRegistrationService.saveRegistration(userRegistrationEntity);
//	        return ResponseEntity.status(HttpStatus.OK).body("{\"redirectUrl\": \"/UserLogin\"}");
//	    }
	 
	 
	 @GetMapping("/getUser")
	 @ResponseBody
	 public List<UserRegistrationEntity> showUser(){
		 return userRegistrationService.getUser();
	 }
	
	 
	 @GetMapping("/getNewUsersCount")
	 @ResponseBody
	 public long getNewUsersCount() {
	     return userRegistrationService.getNewUsersCount();
	 }

	
	 @GetMapping("/getUserById/{id}")
	 @ResponseBody
	 public UserRegistrationEntity showUserId(@PathVariable Long id) {
		 return userRegistrationService.getUserId(id);
	 }
	 
	 
	 
	//this is working for login user
//	@PostMapping("/UserLogin")
//	@ResponseBody
//	public ResponseEntity<Map<String, String>> loginPage(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//		UserRegistrationEntity authenUser = userRegistrationService.login(userRegistrationEntity.getEmail(), userRegistrationEntity.getPassword());
//
//	    if (authenUser != null && authenUser.getPassword().equals(userRegistrationEntity.getPassword())) {
//	        // Authentication successful, return redirect URL
//	        Map<String, String> response = new HashMap<>();
//	        response.put("redirectUrl", "/home"); // Redirect to index page upon successful login
//	        return ResponseEntity.ok(response);
//	    } else {
//	        // Authentication failed, return error message
//	        Map<String, String> response = new HashMap<>();
//	        response.put("error", "Invalid email or password.");
//	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	    }
//	}
	
	 
	 
	 
	 //this is working method for user login 
//	 @PostMapping("/UserLogin")
//	    @ResponseBody
//	    public ResponseEntity<Map<String, String>> loginPage(@RequestBody UserRegistrationEntity userRegistrationEntity, HttpSession session) {
//	        UserRegistrationEntity authenUser = userRegistrationService.login(userRegistrationEntity.getEmail(), userRegistrationEntity.getPassword());
//
//	        if (authenUser != null && authenUser.getPassword().equals(userRegistrationEntity.getPassword())) {
//	            // Authentication successful, set user in session
//	            session.setAttribute("loggedInUser", authenUser);
//	            Map<String, String> response = new HashMap<>();
//	            response.put("redirectUrl", "/home"); // Redirect to home page upon successful login
//	            return ResponseEntity.ok(response);
//	        } else {
//	            // Authentication failed, return error message
//	            Map<String, String> response = new HashMap<>();
//	            response.put("error", "Invalid email or password.");
//	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//	        }
//	    }
	 
	 
	 //this is also working method for user login 
//		@PostMapping("/UserLogin")
//		@ResponseBody
//		public ResponseEntity<Map<String, Object>> loginPage(@RequestBody UserRegistrationEntity userRegistrationEntity,
//				HttpSession session) {
//			UserRegistrationEntity authenUser = userRegistrationService.login(userRegistrationEntity.getEmail(),
//					userRegistrationEntity.getPassword());
//
//			Map<String, Object> response = new HashMap<>();
//			if (authenUser != null && authenUser.getPassword().equals(userRegistrationEntity.getPassword())) {
//				// Authentication successful, set user in session
//				session.setAttribute("loggedInUser", authenUser);
//				response.put("redirectUrl", "/home"); // Redirect to home page upon successful login
//				response.put("userId", authenUser.getId()); // Include user ID in the response
//				return ResponseEntity.ok(response);
//			} else {
//				// Authentication failed, return error message
//				response.put("error", "Invalid email or password.");
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//			}
//		}
//
		@PostMapping("/UserLogin")
		@ResponseBody
		public ResponseEntity<Map<String, Object>> loginPage(@RequestParam("email") String email,
				@RequestParam("password") String password, HttpSession session) {

			System.out.println("Received login request with email: " + email + " and password: " + password);

			UserRegistrationEntity authenUser = userRegistrationService.login(email, password);

			Map<String, Object> response = new HashMap<>();
			if (authenUser != null && authenUser.getPassword().equals(password)) {
				// Authentication successful, set user in session
				session.setAttribute("loggedInUser", authenUser);
				response.put("redirectUrl", "/home");
				response.put("userId", authenUser.getId());
				return ResponseEntity.ok(response);
			} else {
				// Authentication failed, return error message
				response.put("error", "Invalid email or password.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}
	 

		@GetMapping("/logout")
	    public String logout(HttpSession session) {
	        session.invalidate();
	        System.out.println("mjhjg");
	        return "redirect:/home"; // Ensure this URL points to your home page
	    }
		
//	    @PostMapping("/saveRegistration")
//	    @ResponseBody
//	    public ResponseEntity<?> addRegistration(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//	        if (userRegistrationService.emailExists(userRegistrationEntity.getEmail())) {
//	            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
//	        }
//
//	        userRegistrationEntity.setRegistrationDate(LocalDate.now());
//	        userRegistrationService.saveRegistration(userRegistrationEntity);
//
//	        // Send confirmation email
//	        String subject = "Registration Successful";
//	        String body = "Thank you for registering on Online Exam Portal. Your registration is successfully done.";
//	        emailService.sendSimpleEmail(userRegistrationEntity.getEmail(), subject, body);
//
//	        return ResponseEntity.status(HttpStatus.OK).body("{\"redirectUrl\": \"/UserLogin\"}");
//	    }
	    
//	    @PostMapping("/saveRegistration")
//	    @ResponseBody
//	    public ResponseEntity<?> addRegistration(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//	        if (userRegistrationService.emailExists(userRegistrationEntity.getEmail())) {
//	            return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
//	        }
//
//	        userRegistrationEntity.setRegistrationDate(LocalDate.now());
//	        userRegistrationService.saveRegistration(userRegistrationEntity);
//
//	        // Send confirmation email
//	        String subject = "Registration Successful";
//	        String body = "Dear " + userRegistrationEntity.getFirstName() + ", \n\n" +
//	                      "Thank you for registering on Online Exam Portal.\n" +
//	                      "Your registration has been successfully completed. You can now log in to your account and start exploring our platform.\n\n" +
//	                      "If you have any questions or need assistance, please feel free to contact our support team.\n\n" +
//	                      "Best regards,\n" +
//	                      "The Online Exam Portal Team";
//	        emailService.sendSimpleEmail(userRegistrationEntity.getEmail(), subject, body);
//
//	        return ResponseEntity.status(HttpStatus.OK).body("{\"redirectUrl\": \"/UserLogin\"}");
//	    }

		@PostMapping("/saveRegistration")
		@ResponseBody
		public ResponseEntity<?> addRegistration(@RequestParam String firstName, @RequestParam String lastName,
				@RequestParam String email, @RequestParam String mobileNum, @RequestParam String password) {

			if (userRegistrationService.emailExists(email)) {
				return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
			}

			UserRegistrationEntity userRegistrationEntity = new UserRegistrationEntity();
			userRegistrationEntity.setFirstName(firstName);
			userRegistrationEntity.setLastName(lastName);
			userRegistrationEntity.setEmail(email);
			userRegistrationEntity.setMobileNum(mobileNum);
			userRegistrationEntity.setPassword(password);
			userRegistrationEntity.setConfirmPassword(password);
			userRegistrationEntity.setRegistrationDate(LocalDate.now());

			userRegistrationService.saveRegistration(userRegistrationEntity);

			// Send confirmation email
			String subject = "Registration Successful";
			String body =  "Dear " + firstName + ", <br><br>"
			        + "Thank you for registering on Online Exam Portal.<br>"
			        + "Your registration has been successfully completed. You can now log in to your account and start exploring our platform.<br><br>"
			        + "If you have any questions or need assistance, please feel free to contact our support team.<br><br>"
			        + "Best regards,<br>"
			        + "The Online Exam Portal Team";
			emailService.sendSimpleEmail(email, subject, body);

			return ResponseEntity.status(HttpStatus.OK).body("{\"redirectUrl\": \"/UserLogin\"}");
		}

		
		@PutMapping("/editUserDetails")
		@ResponseBody
		public ResponseEntity<UserRegistrationEntity> updateUser(
		    @RequestParam Long id,
		    @RequestParam String firstName,
		    @RequestParam String lastName,
		    @RequestParam String password,
		    @RequestParam String confirmPassword,
		    @RequestParam String mobileNum,
		    @RequestParam String email
		) {
		    try {
		        if (id == null || firstName == null || lastName == null  || password == null || confirmPassword == null || mobileNum == null || email == null) {
		            throw new IllegalArgumentException("All parameters must be provided.");
		        }

				UserRegistrationEntity updatedUser = userRegistrationService.editUser(id, firstName, lastName, password, confirmPassword,
						mobileNum, email);
		        return ResponseEntity.ok(updatedUser);
		    } catch (IllegalArgumentException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		    } catch (Exception e) {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		    }
		}

}
