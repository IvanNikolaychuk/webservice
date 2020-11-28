package com.task.webservice.controller;

import com.task.webservice.model.User;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class UserManagerController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/adminUserManaging.html", "/admin-user-managing.html"})
    public String userDataUpdate(Model model) {
        addCommonAttributes(userService, model);
        List<User> users = userService.findAllExceptCurrent(getCurrentUserName());
        model.addAttribute("users", users);

        return "admin/user-managing.html";
    }

    @RequestMapping(value = {"/removeUser"}, method = RequestMethod.POST)
    public String removeUser(@ModelAttribute("user") User user) {
        userService.remove(userService.findByEmail(user.getUsername()));

        return "redirect:/adminUserManaging.html";
    }

}
