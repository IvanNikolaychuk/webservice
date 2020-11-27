package com.task.webservice.controller;

import com.task.webservice.model.Role;
import com.task.webservice.model.User;
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

    @RequestMapping(value = {"/login.html", "/login"})
    public String login() {
        return "login.html";
    }

    @RequestMapping("/logout.html")
    public String loggingOut() {
        logout();
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
        userService.recordLogin(getCurrentUserName());

        if (isAdmin(userService)) {
            return "redirect:/admin-homepage.html";

        } else {
            model.addAttribute("billingProfile", profileService.getDefaultBillingProfile(getCurrentUserName()));
            model.addAttribute("shippingProfile", profileService.getDefaultShippingProfile(getCurrentUserName()));
            model.addAttribute("readMessagesCounter", messageService.numberOfReadMessages(getCurrentUserName()));
            model.addAttribute("unreadMessagesCounter", messageService.numberOfUnreadMessages(getCurrentUserName()));

            return "homepage.html";
        }
    }

    @RequestMapping("/admin-homepage.html")
    public String homepageAdmin(Model model) {
        addCommonAttributes(userService, model);
        userService.recordLogin(getCurrentUserName());
        model.addAttribute("users", userService.findByRole(Role.ROLE_USER.name()));
        model.addAttribute("messages", messageService);
        model.addAttribute("answeredMessagesCounter", messageService.numberOfAnsweredMessagesForAdmin(getCurrentUserName()));
        model.addAttribute("unansweredMessagesCounter", messageService.numberOfUnansweredMessagesForAdmin(getCurrentUserName()));

        return "admin/homepage.html";
    }

    @RequestMapping("/header.html")
    public String header() {
        return "header.html";
    }

    @RequestMapping("/admin-header.html")
    public String adminHeader() {
        return "admin/header.html";
    }
}
