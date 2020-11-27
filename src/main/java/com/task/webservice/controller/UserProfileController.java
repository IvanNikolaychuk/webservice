package com.task.webservice.controller;

import com.task.webservice.model.Profile;
import com.task.webservice.service.ProfileService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserProfileController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @RequestMapping(value = {"/userProfile.html", "/user-profile.html"})
    public String viewProfile(Model model) {
        addCommonAttributes(userService, model);
        return "user-profile.html";
    }

    @RequestMapping(value = {"/adminProfile.html", "/admin-profile.html"})
    public String viewProfileAdmin(Model model) {
        addCommonAttributes(userService, model);
        return  isAdmin(userService) ? "admin/profile.html" : "user-profile.html";
    }

    @RequestMapping(value = "/profileUpdate", method = RequestMethod.POST)
    public String profileUpdate(@ModelAttribute("profile") Profile updatedProfile) {
        profileService.updateProfileData(getCurrentUserName(), updatedProfile);
        userService.recordProfileUpdate(getCurrentUserName());
        return isAdmin(userService) ? "redirect:/admin-profile.html" : "redirect:/user-profile.html";
    }

    @RequestMapping(value = "/profileDelete", method = RequestMethod.POST)
    public String profileDelete(@ModelAttribute("profile") Profile updatedProfile) {
        profileService.deleteProfileData(getCurrentUserName(), updatedProfile);
        userService.recordProfileUpdate(getCurrentUserName());
        return isAdmin(userService) ? "redirect:/admin-profile.html" : "redirect:/user-profile.html";
    }
}
