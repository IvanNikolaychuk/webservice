package com.task.webservice.controller;

import com.task.webservice.model.Message;
import com.task.webservice.service.MessageService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SupportPageController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = {"/support.html", "/support"})
    public String viewSupportPage(Model model) {
        addCommonAttributes(userService, model);
        return "support.html";
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String addMessage(@ModelAttribute("profile") Message message) {
        messageService.save(getCurrentUser(), message);
        return "redirect:/support.html?messageSent=true";
    }

}
