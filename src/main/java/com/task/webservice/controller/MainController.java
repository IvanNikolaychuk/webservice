package com.task.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@RequestMapping("/login.html")
	public String login() {
		return "login.html";
	}

	@RequestMapping(value = "/perform_login", method = RequestMethod.POST)
	public String login(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

	@RequestMapping("/registration.html")
	public String registration() {
		return "registration.html";
	}
}
