package com.task.webservice.controller;

import com.task.webservice.model.Message;
import com.task.webservice.model.Pair;
import com.task.webservice.model.User;
import com.task.webservice.service.MessageService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public String addMessage(@ModelAttribute("message") Message message) {
        messageService.save(getCurrentUserName(), message);
        return "redirect:/support.html?messageSent=true";
    }

    @RequestMapping(value = {"/removeMessage"}, method = RequestMethod.POST)
    public String removeMessage(@ModelAttribute("message") Message message) {
        messageService.remove(message);
        return "redirect:/adminMessages.html";
    }

    @RequestMapping(value = {"/adminMessages.html", "/admin-messages"})
    public String getMessages(Model model) {
        model.addAttribute("senderToMessages", messageService.findUnansweredMessages(getCurrentUser(userService).getId()));
        return "admin/inbox.html";
    }

    @RequestMapping(value = {"/adminMessageReply.html", "/adminMessageReply"})
    public String getMessageReplyForm(Model model, @ModelAttribute("message") Message message) {
        Pair<User, Message> senderToMessage = messageService.findUnansweredMessages(getCurrentUser(userService).getId())
                .stream()
                .filter(entry -> entry.getRight().getId().equals(message.getId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("senderToMessage", senderToMessage);
        return "admin/message-reply.html";
    }

    @RequestMapping(value = "/replyOnMessage", method = RequestMethod.POST)
    public String reply(@ModelAttribute("message") Message message) {
        messageService.replyTo(message);
        return "redirect:/adminMessages.html";
    }
}
