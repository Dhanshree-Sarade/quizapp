package com.ezio.OnlineExamPortal.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezio.OnlineExamPortal.entity.Questions;
import com.ezio.OnlineExamPortal.helper.ExcelHelper;
import com.ezio.OnlineExamPortal.repository.QuestionsRepository;
import com.ezio.OnlineExamPortal.service.QuestionsService;

@Controller
public class QuestionsController {

	@Autowired
	private QuestionsService questionsService;

	@Autowired
	QuestionsRepository repo;

	@GetMapping("/count/{topic_Id}")
	@ResponseBody
	public Integer getcount(@PathVariable long topic_Id) {
		return repo.getcount(topic_Id);
	}

	@GetMapping("/sum/{topic_Id}")
	@ResponseBody
	public Integer getSum(@PathVariable long topic_Id) {
		return repo.getSum(topic_Id);
	}

//	//this is correct code to upload only excel sheet 
//	@PostMapping("/uploadExcel")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please select a file to upload.");
//        }
//
//        if (!ExcelHelper.checkExcelFormat(file)) {
//            return ResponseEntity.badRequest().body("Please upload an Excel file!");
//        }
//
//        try {
//        	questionsService.saveQuestionsFromExcel(file);
//            return ResponseEntity.ok().body("Uploaded successfully: " + file.getOriginalFilename());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("Failed to upload file: " + e.getMessage());
//        }
//    }

	@PostMapping("/uploadExcel/{topicId}")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long topicId) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Please select a file to upload.");
		}

		if (!ExcelHelper.checkExcelFormat(file)) {
			return ResponseEntity.badRequest().body("Please upload an Excel file!");
		}

		try {
			questionsService.saveQuestionsFromExcel(file, topicId);

			// Update the status of the topic to true indicating the file is uploaded
			// topicService.updateUploadStatus(topicId, true);

			return ResponseEntity.ok().body("Uploaded successfully: " + file.getOriginalFilename());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to upload file: " + e.getMessage());
		}
	}

//	  @PostMapping("/uploadExcel") 
//	  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Long topicId) { 
//		  
//		  if(file.isEmpty()) { 
//			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload."); 
//			  }
//		  if(!ExcelHelper.checkExcelFormat(file)) { 
//			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload an Excel file!"); 
//			  } 
//		  	try {
//		  		questionsService.saveQuestionsFromExcel(file, topicId); 
//		  		return ResponseEntity.status(HttpStatus.OK).body("Uploaded successfully: " + file.getOriginalFilename()); 
//		  		} 
//		    catch (Exception e) { 
//		    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage()); 
//		    	} 
//		  	}

	// Get All questions
	@GetMapping("/questions")
	@ResponseBody
	public List<Questions> getAllQuestions() {
		return this.questionsService.getAllQuestions();
	}

	// Get questions by topicId
	@GetMapping("/questionsByTopicId/{topicId}/questions")
	@ResponseBody
	public ResponseEntity<List<Questions>> showAllQuestions(@PathVariable Long topicId) {
		List<Questions> questions = questionsService.getQuestions(topicId);
		return ResponseEntity.ok().body(questions);
	}

//	  @PutMapping("/{topicId}/uploadExcel")
//	  @ResponseBody
//	    public ResponseEntity<Topic> updateTopicWithExcel(@PathVariable Long topicId,
//	                                                     @RequestParam("file") MultipartFile file) throws Exception {
//	        try {
//	        	System.out.println("get a id:="+topicId);
//	            Topic updatedTopic = questionsService.updateTopicWithExcel(topicId, file);
//	            return ResponseEntity.ok(updatedTopic);
//	        } catch (IOException e) {
//	        	System.err.println("some is ");
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	        }
//	    }
//	  

//	  @PutMapping("/{topicId}/uploadExcel")
//	  @ResponseBody
//	    public String updateTopicWithExcel(@PathVariable Long topicId,
//	                                                     @RequestParam("file") MultipartFile file) throws Exception {
//	        try {
//	        	System.out.println("get a id:="+topicId);
//	             questionsService.updateTopicWithExcel(topicId, file);
//	            return "Working";
//	        } catch (Exception e) {
//	        	System.err.println("some is "+e);
//	            return "not working"; 
//	        }
//	    }

	@RequestMapping("/{topicId}/uploadExcel")
	@ResponseBody
	public ResponseEntity<String> updateTopicWithExcel(@PathVariable Long topicId,
	        @RequestParam("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("Please select a file to upload.");
	    }

	    if (!ExcelHelper.checkExcelFormat(file)) {
	        return ResponseEntity.badRequest().body("Please upload an Excel file!");
	    }

	    try {
	        questionsService.updateTopicWithExcel(topicId, file);
	        return ResponseEntity.ok().body("Uploaded and updated successfully: " + file.getOriginalFilename());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to upload and update file: " + e.getMessage());
	    }
	}


	@RequestMapping("/deleteByTopic/{topicId}")
	public ResponseEntity<String> deleteQuestionsByTopicId(@PathVariable Long topicId) {
		try {
			questionsService.deleteQuestionsByTopicId(topicId);
			return ResponseEntity.ok("Questions deleted successfully for topic ID: " + topicId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete questions: " + e.getMessage());
		}
	}

}
