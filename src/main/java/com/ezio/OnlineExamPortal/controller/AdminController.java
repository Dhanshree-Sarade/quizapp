package com.ezio.OnlineExamPortal.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezio.OnlineExamPortal.entity.Admin;
import com.ezio.OnlineExamPortal.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/index")
	public String index(HttpSession session) {
		Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "demo/index";
	}

	@GetMapping("/login")
	public String login() {
		return "demo/login";
	}

	@GetMapping("/upload")
	public String upload(HttpSession session) {
		// System.out.println("kjkdnjnjd");
		Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "demo/upload";
	}

	@GetMapping("/datatable")
	public String datatable(HttpSession session) {
		Admin loggedInUser = (Admin) session.getAttribute("loggedInUser");
		if (loggedInUser == null) {
			return "redirect:/login";
		}
		return "demo/table-datatable";
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<Map<String, String>> loginPage(@RequestBody Admin admin, HttpSession session) {
		Admin authenAdmin = adminService.login(admin.getUsername(), admin.getPassword());

		if (authenAdmin != null && authenAdmin.getPassword().equals(admin.getPassword())) {
			// Authentication successful, store admin in session
			session.setAttribute("loggedInUser", authenAdmin);

			// Return redirect URL
			Map<String, String> response = new HashMap<>();
			response.put("redirectUrl", "/index"); // Redirect to index page upon successful login
			return ResponseEntity.ok(response);
		} else {
			// Authentication failed, return error message
			Map<String, String> response = new HashMap<>();
			response.put("error", "Invalid username or password.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@GetMapping("/adminLogout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login"; // Redirect to login page after logout
	}

}
