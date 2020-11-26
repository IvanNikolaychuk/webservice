package com.task.webservice.controller;

import com.task.webservice.controller.cofig.MyUserDetails;
import com.task.webservice.model.Employee;
import com.task.webservice.model.User;
import com.task.webservice.service.EmailService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

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

		if (userService.get(user.getUsername()) == null) {
			userService.save(user);
			emailService.sendEmailUponUserRegistration(user);
		}

		return "redirect:/";
	}

	@RequestMapping("/homepage.html")
	public String homepage(Model model) {
		MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "homepage.html";
	}
}
