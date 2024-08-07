package com.ezio.OnlineExamPortal.UserController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezio.OnlineExamPortal.UserEntity.ExamResults;
import com.ezio.OnlineExamPortal.UserEntity.UserRegistrationEntity;
import com.ezio.OnlineExamPortal.UserRepository.ExamResultRepository;
import com.ezio.OnlineExamPortal.UserRepository.UserRegistrationRepository;
import com.ezio.OnlineExamPortal.UserService.EmailService;
import com.ezio.OnlineExamPortal.UserService.ExamResultService;
import com.ezio.OnlineExamPortal.entity.Topic;
import com.ezio.OnlineExamPortal.repository.TopicRepository;

@Controller
public class ExamResultController {
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;
	
	@Autowired
	private ExamResultRepository examResultRepository;
	
	@Autowired
	private ExamResultService examResultService;
	
	@Autowired
	private TopicRepository topicRepository;
	
	
	//this is working method for store exam result in database
//	@RequestMapping(value = "/ExamResultSave", method = RequestMethod.POST)
//	public ResponseEntity<?> saveExamResult(@RequestBody ExamResults examResults) {
//	    try {
//	        // Check if userRegistrationEntity and its ID are valid
//	        UserRegistrationEntity userRegistrationEntity = examResults.getUserRegistrationEntity();
//	        if (userRegistrationEntity == null || userRegistrationEntity.getId() == null) {
//	            return ResponseEntity.badRequest().body("User ID must not be null.");
//	        }
//
//	        Integer userId = userRegistrationEntity.getId();
//	        UserRegistrationEntity user = userRegistrationRepository.findById(userId).orElse(null);
//	        if (user == null) {
//	            return ResponseEntity.badRequest().body("User not found.");
//	        }
//
//	        examResults.setUserRegistrationEntity(user);
//	        ExamResults savedResults = examResultService.saveData(examResults);
//	        return ResponseEntity.ok(savedResults);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//	    }
//	}

	
	 @GetMapping("/getAllResult")
	 @ResponseBody
	    public ResponseEntity<List<ExamResults>> getAllResults() {
	        List<ExamResults> results = examResultService.getAllResults();
	        return ResponseEntity.ok(results);
	    }
	
	
//	@PostMapping("/ExamResultSave")
//    public ResponseEntity<?> saveExamResult(@RequestBody ExamResults examResults) {
//        // Save the exam result
//        examResultRepository.save(examResults);
//
//        UserRegistrationEntity userRegistrationEntity = examResults.getUserRegistrationEntity();
//        // Fetch the user
//        Integer userId = userRegistrationEntity.getId();
//        UserRegistrationEntity user = userRegistrationRepository.findById(userId).orElse(null);
//
//        if (user != null) {
//            // Prepare the email content
//            String subject = "Your Exam Report";
//            String text = "Dear " + user.getFirstName() + ",\n\n" +
//                          "Here is your exam report:\n" +
//                        //  "Topic: " + examResults.getTopicName() + "\n" +
//                        //  "Topic Description: " + examResults.getTopicDescription() + "\n" +
//                          "Total Questions: " + examResults.getTotalQuestions() + "\n" +
//                          "Correct Answers: " + examResults.getCorrectAnswers() + "\n" +
//                          "Attempted Questions: " + examResults.getAttemptedQuestions() + "\n" +
//                          "Percentage: " + examResults.getPercentage() + "%\n" +
//                          "Result: " + examResults.getResult() + "\n\n" +
//                          "Best regards,\nYour Exam Portal Team";
//
//            // Send the email
//            emailService.sendSimpleEmail(user.getEmail(), subject, text);
//        }
//
//        return ResponseEntity.ok("Exam result saved and email sent.");
//    }
	
	
	
	//this is working method
//	@PostMapping("/ExamResultSave/{topicId}")
//	public ResponseEntity<?> saveExamResult(@RequestBody ExamResults examResults, @PathVariable Long topicId) {
//	    // Save the exam result
//		examResults.setTimestamp(LocalDateTime.now());
//	    //examResultRepository.save(examResults);
//		examResultService.saveExamResult(topicId, examResults);
//	    
//	    // Fetch or create the Topic entity by its ID
//        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
//
//     // Set the Topic on the ExamResults instance
//        examResults.setTopic(topic);
//
//
//	    UserRegistrationEntity userRegistrationEntity = examResults.getUserRegistrationEntity();
//	    // Fetch the user
//	    Integer userId = userRegistrationEntity.getId();
//	    UserRegistrationEntity user = userRegistrationRepository.findById(userId).orElse(null);
//
//	    if (user != null) {
//	        // Prepare the email content
//	        String subject = "Your Exam Report";
//	        String resultMessage = examResults.getPercentage() > 50 ? "Congratulations!" : "Oops, try again.";
//	        
//	        String text = "Dear " + user.getFirstName() + ",\n\n" +
//	                      "Here is your exam report:\n" +
//	                      "Total Questions: " + examResults.getTotalQuestions() + "\n" +
//	                      "Correct Answers: " + examResults.getCorrectAnswers() + "\n" +
//	                      "Attempted Questions: " + examResults.getAttemptedQuestions() + "\n" +
//	                      "Percentage: " + examResults.getPercentage() + "%\n" +
//	                      "Result: " + examResults.getResult() + "\n\n" +
//	                      resultMessage + "\n\n" +
//	                      "Best regards,\nYour Exam Portal Team";
//
//	        // Send the email
//	        emailService.sendSimpleEmail(user.getEmail(), subject, text);
//	    }
//
//	    return ResponseEntity.ok("Exam result saved and email sent.");
//	}
	
//	@PostMapping("/ExamResultSave")
//	public ResponseEntity<?> saveExamResult(@RequestParam Long topicId, @RequestParam Integer totalQuestions,
//			@RequestParam Integer correctAnswers, @RequestParam Integer attemptedQuestions,
//			@RequestParam Double percentage, @RequestParam String result, @RequestParam Long userId) {
//		
//		System.out.println("Received topicId: " + topicId);
//	    System.out.println("Received userId: " + userId);
//
//		// Create new ExamResults object and set properties
//		ExamResults examResults = new ExamResults();
//		examResults.setTotalQuestions(totalQuestions);
//		examResults.setCorrectAnswers(correctAnswers);
//		examResults.setAttemptedQuestions(attemptedQuestions);
//		examResults.setPercentage(percentage);
//		examResults.setResult(result);
//		examResults.setTimestamp(LocalDateTime.now());
//
//		// Fetch or create the Topic entity by its ID
//		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
//		// Set the Topic on the ExamResults instance
//		examResults.setTopic(topic);
//		System.err.println("Topic id is : " + topic.getTopicId());
//
//		// Fetch the user
//		UserRegistrationEntity user = userRegistrationRepository.findById(userId).orElse(null);
//
//		System.err.println("User id is : " + user.getId());
//		
//		if (user != null) {
//			// Set the user on the ExamResults instance
//			examResults.setUserRegistrationEntity(user);
//
//			// Save the exam result
//			examResultService.saveExamResult(topicId, examResults);
//
//			// Prepare the email content
//			String subject = "Your Exam Report";
//			String resultMessage = examResults.getPercentage() > 50 ? "Congratulations!" : "Oops, try again.";
//
//			String text = "Dear " + user.getFirstName() + ",\n\n" +
//		              "Here is your exam report:\n" +
//		              "Total Questions: " + examResults.getTotalQuestions() + "\n" +
//		              "Correct Answers: " + examResults.getCorrectAnswers() + "\n" +
//		              "Attempted Questions: " + examResults.getAttemptedQuestions() + "\n" +
//		              "Percentage: " + examResults.getPercentage() + "%\n" +
//		              "Result: " + examResults.getResult() + "\n\n" +
//		              resultMessage + "\n\n" +
//		              "Best regards,\n" +
//		              "Your Exam Portal Team";
//
//
//			// Send the email
//			emailService.sendSimpleEmail(user.getEmail(), subject, text);
//		} else {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
//		}
//
//		return ResponseEntity.ok("Exam result saved and email sent.");
//	}

	@PostMapping("/ExamResultSave")
	public ResponseEntity<?> saveExamResult(@RequestParam Long topicId, @RequestParam Integer totalQuestions,
	        @RequestParam Integer correctAnswers, @RequestParam Integer attemptedQuestions,
	        @RequestParam Double percentage, @RequestParam String result, @RequestParam Long userId) {

	    System.out.println("Received topicId: " + topicId);
	    System.out.println("Received userId: " + userId);

	    // Create new ExamResults object and set properties
	    ExamResults examResults = new ExamResults();
	    examResults.setTotalQuestions(totalQuestions);
	    examResults.setCorrectAnswers(correctAnswers);
	    examResults.setAttemptedQuestions(attemptedQuestions);
	    examResults.setPercentage(percentage);
	    examResults.setResult(result);
	    examResults.setTimestamp(LocalDateTime.now());

	    // Fetch or create the Topic entity by its ID
	    Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException("Topic not found"));
	    // Set the Topic on the ExamResults instance
	    examResults.setTopic(topic);
	    examResults.setTopicName(topic.getTopicName()); // Set topic name
	    examResults.setTopicDescription(topic.getDescription()); // Set topic description
	    System.err.println("Topic id is : " + topic.getTopicId());

	    // Fetch the user
	    UserRegistrationEntity user = userRegistrationRepository.findById(userId).orElse(null);

	    System.err.println("User id is : " + user.getId());

	    if (user != null) {
	        // Set the user on the ExamResults instance
	        examResults.setUserRegistrationEntity(user);

	        // Save the exam result
	        examResultService.saveExamResult(topicId, examResults);

	        // Prepare the email content
	        String subject = "Your Exam Report";
	        String resultMessage = examResults.getPercentage() > 50 ? "Congratulations!" : "Oops, try again.";
	        String color = examResults.getPercentage() > 50 ? "green" : "red";
	        String text = "<html><body>" +
	                "<p><strong>Dear " + user.getFirstName() + ",</strong></p>" +
	                "<br>" +
	                "<p>Here is your exam report:</p>" +
	                "<ul>" +
	                "<li>Topic Name: " + examResults.getTopicName() + "</li>" +
	                "<li>Topic Description: " + examResults.getTopicDescription() + "</li>" +
	                "<li>Total Questions: " + examResults.getTotalQuestions() + "</li>" +
	                "<li>Correct Answers: " + examResults.getCorrectAnswers() + "</li>" +
	                "<li>Attempted Questions: " + examResults.getAttemptedQuestions() + "</li>" +
	                "<li>Percentage: <span style='color:" + color + ";'>" + examResults.getPercentage() + "%</span></li>" +
	                "<li>Result: <span style='color:" + color + ";'>" + examResults.getResult() + "</span></li>" +
	                "</ul>" +
	                "<p><strong>" + resultMessage + "</strong></p>" +
	                "<br>" +
	                "<p>Best regards,<br>Your Exam Portal Team</p>" +
	                "</body></html>";

	        // Send the email
	        emailService.sendSimpleEmail(user.getEmail(), subject, text);
	        
	        // Send the email to the additional recipient (hard-coded)
	        String additionalEmail = "hrezio2022@gmail.com"; // Replace with the actual email
	        emailService.sendSimpleEmail(additionalEmail, subject, text);

	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
	    }

	    return ResponseEntity.ok("Exam result saved and email sent.");
	}


	
	
//	@PostMapping("/save/{topicId}")
//    public ResponseEntity<ExamResults> saveExamResult(@PathVariable Long topicId, @RequestBody ExamResults examResults) {
//        ExamResults savedResult = examResultService.saveExamResult(topicId, examResults);
//        return ResponseEntity.ok(savedResult);
//    }
	
	
	
	
	
	
	
	
	
	
	

	@GetMapping("/ExamResultById/{examResultId}")
	@ResponseBody
	public ExamResults getResult(@PathVariable Long examResultId) {
		return examResultService.getDataById(examResultId);
	}
	
	
	@GetMapping("/latestExamResult/{id}")
	public ResponseEntity<?> getLatestExamResult(@PathVariable Integer id) {
	    ExamResults latestExamResult = examResultRepository.findTopByUserRegistrationEntityIdOrderByTimestampDesc(id);
	    if (latestExamResult != null) {
	        return ResponseEntity.ok(latestExamResult);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No exam results found for this user");
	    }
	}
	 
	
	
	 @GetMapping("/usersAttemptedExams/{userId}")
	    public ResponseEntity<List<ExamResults>> getExamResultsByUserId(@PathVariable Long userId) {
	        List<ExamResults> examResults = examResultService.getExamResultsByUserId(userId);
	        return ResponseEntity.ok(examResults);
	    }
	 
	  
	
	
}
