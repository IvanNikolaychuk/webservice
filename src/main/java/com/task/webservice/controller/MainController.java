package com.task.webservice.controller;

import com.task.webservice.model.Employee;
import com.task.webservice.model.User;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	@RequestMapping("/login.html")
	public String login() {
		return "login.html";
	}

	@RequestMapping("/registration.html")
	public String registration(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "registration.html";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("employee") User user) {
		user.setRole("USER");
		user.setEnabled(true);
		userService.save(user);

		return "redirect:/";
	}
}
