package com.task.webservice.controller;

import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserSupportPageController extends AbstractController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/support.html", "/support"})
    public String viewSupportPage(Model model) {
        addCommonAttributes(userService, model);
        return "support.html";
    }
}
