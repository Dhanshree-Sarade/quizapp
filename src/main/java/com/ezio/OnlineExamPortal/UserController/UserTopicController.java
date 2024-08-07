package com.ezio.OnlineExamPortal.UserController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezio.OnlineExamPortal.UserService.UserTopicService;
import com.ezio.OnlineExamPortal.entity.Topic;

@Controller
public class UserTopicController {

	@Autowired
	private UserTopicService userTopicService;
	
	@GetMapping("/topics/{language}")
	@ResponseBody
	public List<Topic> getTopicByLanguage(@PathVariable String language) {
		return userTopicService.getByLanguage(language);
	}
}
