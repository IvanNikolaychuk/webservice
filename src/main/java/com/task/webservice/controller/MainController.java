package com.task.webservice.controller;

import com.task.webservice.model.User;
import com.task.webservice.service.EmailService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController extends AbstractController {
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
    public String saveUser(@ModelAttribute("user") User user) {
        user.setRole("USER");
        user.setEnabled(true);

        if (userService.get(user.getUsername()) == null) {
            userService.saveNewUser(user);
            emailService.sendEmailUponUserRegistration(user);
        }

        return "redirect:/";
    }

    @RequestMapping("/homepage.html")
    public String homepage(Model model) {
        addUserAttribute(userService, model);
        return "homepage.html";
    }

    @RequestMapping(value = "/userDataUpdate", method = RequestMethod.POST)
    public String userDataUpdate(@ModelAttribute("user") User updatedUser) {
        userService.updateUserDate(getCurrentUser(), updatedUser);
        return "redirect:/user-profile.html";
    }

    @RequestMapping("/header.html")
    public String header() {
        return "header.html";
    }
}
