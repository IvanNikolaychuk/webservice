package com.task.webservice.controller;

import com.task.webservice.model.User;
import com.task.webservice.service.EmailService;
import com.task.webservice.service.MessageService;
import com.task.webservice.service.ProfileService;
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
    private MessageService messageService;

    @Autowired
    private ProfileService profileService;

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

    @RequestMapping("/homepage.html")
    public String homepage(Model model) {
        addCommonAttributes(userService, model);
        userService.recordLogin(getCurrentUser());
        model.addAttribute("billingProfile", profileService.getDefaultBillingProfile(getCurrentUser()));
        model.addAttribute("shippingProfile", profileService.getDefaultShippingProfile(getCurrentUser()));
        model.addAttribute("readMessagesCounter", messageService.numberOfReadMessages(getCurrentUser()));
        model.addAttribute("unreadMessagesCounter", messageService.numberOfUnreadMessages(getCurrentUser()));

        return "homepage.html";
    }

    @RequestMapping("/header.html")
    public String header() {
        return "header.html";
    }
}
