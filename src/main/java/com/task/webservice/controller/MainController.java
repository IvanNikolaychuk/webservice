package com.task.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}
}
