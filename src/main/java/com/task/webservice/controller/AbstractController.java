package com.task.webservice.controller;

import com.task.webservice.controller.cofig.MyUserDetails;
import com.task.webservice.model.CreditCard;
import com.task.webservice.model.Message;
import com.task.webservice.model.Profile;
import com.task.webservice.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

public class AbstractController {

    public String getCurrentUser() {
        return ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }


    public void addUserAttribute(UserService userService, Model model) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.get(user.getUsername()));
        model.addAttribute("newProfile", new Profile());
        model.addAttribute("newCard", new CreditCard());
        model.addAttribute("newMessage", new Message());
    }
}
