package com.task.webservice.controller;

import com.task.webservice.controller.cofig.MyUserDetails;
import com.task.webservice.model.Profile;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping("/profile.html")
    public String viewProfile(Model model) {
        addUserAttribute(userService, model);
        return "profile.html";
    }

    @RequestMapping(value = "/profileUpdate", method = RequestMethod.POST)
    public String profileUpdate(@ModelAttribute("profile") Profile updatedProfile) {
        userService.updateProfileData(getCurrentUser(), updatedProfile);
        return "redirect:/profile.html";
    }

    @RequestMapping(value = "/profileDelete", method = RequestMethod.POST)
    public String profileDelete(@ModelAttribute("profile") Profile updatedProfile) {
        userService.deleteProfileData(getCurrentUser(), updatedProfile);
        return "redirect:/profile.html";
    }
}
