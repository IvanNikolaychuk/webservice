package com.task.webservice.controller;

import com.task.webservice.controller.cofig.MyUserDetails;
import com.task.webservice.model.CreditCard;
import com.task.webservice.model.Message;
import com.task.webservice.model.Profile;
import com.task.webservice.model.User;
import com.task.webservice.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class AbstractController {

    public String getCurrentUserName() {
        return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    }

    public User getCurrentUser(UserService userService) {
        return userService.findByEmail(getCurrentUserName());
    }

    public boolean isAdmin(UserService userService) {
        return getCurrentUser(userService).isAdmin();
    }

    public void addCommonAttributes(UserService userService, Model model) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findByEmail(user.getUsername()));
        model.addAttribute("newProfile", new Profile());
        model.addAttribute("newCard", new CreditCard());
        model.addAttribute("newMessage", new Message());
    }
}
