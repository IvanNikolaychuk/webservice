package com.task.webservice.controller;

import com.task.webservice.model.Role;
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
public class AdminSupportPageController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/adminSupport.html", "/admin-support.html"})
    public String userDataUpdate(Model model) {
        addCommonAttributes(userService, model);
        List<User> users = userService.findByRole(Role.ROLE_ADMIN.name());
        model.addAttribute("users", users);

        return "admin/support.html";
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public String addAdminUser(@ModelAttribute("newAdmin") User user) {
        user.setRole(Role.ROLE_ADMIN.name());
        user.setEnabled(true);
        userService.saveNewUser(user);

        return "redirect:/adminSupport.html";
    }

}
