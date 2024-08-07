package com.ezio.OnlineExamPortal.UserController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	
	@GetMapping("/")
	public String demo() {
		return "user/Home";
	}
	
		
	@GetMapping("/home")
	public String userHome() {
		return "user/Home";
	}
	
	@GetMapping("/UserLogin")
	public String userLogin() {
		return "user/UserLogin"; 
	}
	
	@GetMapping("/registration")
	public String registration() {
		//System.out.println("lkhk");
		return "user/registration";
	}
	
	@GetMapping("/exam")
	public String examPage() {
		return "user/Exam";
	}
	
	@GetMapping("/user-profile")
	public String userProfile() {
		return "user/UserProfile";
	}
	

	@GetMapping("/reportCard")
	public String reportCard() {
		return "user/Report-Card";
	}
	
	@GetMapping("/examPage")
	public String showExamPage() {
		return "user/ExamPage";
	}
	
	@GetMapping("/slickSlider")
		public String slickSlider() {
			return "user/SlickSlider";
		}
	
	@GetMapping("/myTest")
	public String myTest() {
		return "user/MyTest";
	}
	
	@GetMapping("/marathiPage")
	public String showInstructionPage() {
		return "user/InstructionMarathi";
	}
	
	@GetMapping("/hindiPage")
	public String hindiInstructionPage() {
		return "user/ExamInstructionHindi";
	}
	
	@GetMapping("/demoPage")
	public String demoPage() {
		return "user/DemoPage";
	}
	
	@GetMapping("/home/**")
	public String errorPage() {
		return "errorPage";
	}

	@GetMapping("/*")
	public String notFound() {
		return "errorPage";
	}
//	@PostMapping("/saveLogin")
//	@ResponseBody
//	public UserRegistrationEntity addLogin(@RequestBody UserRegistrationEntity userRegistrationEntity) {
//		return userRegistrationService.saveLogin(userRegistrationEntity);
//	}
}
