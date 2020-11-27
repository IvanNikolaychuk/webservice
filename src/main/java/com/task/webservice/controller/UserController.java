package com.task.webservice.controller;

import com.task.webservice.model.User;
import com.task.webservice.service.EmailService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController extends AbstractController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userDataUpdate", method = RequestMethod.POST)
    public String userDataUpdate(@ModelAttribute("user") User updatedUser) {
        userService.updateUserDate(getCurrentUserName(), updatedUser);
        userService.recordProfileUpdate(getCurrentUserName());
        return isAdmin(userService) ? "redirect:/admin-profile.html" : "redirect:/user-profile.html";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        if (userService.get(user.getUsername()) == null) {
            user.setRole("USER");
            user.setEnabled(true);
            userService.saveNewUser(user);
            emailService.sendEmailUponUserRegistration(user);
            return "redirect:/";
        }

        return "redirect:/";
    }

}
