package com.task.webservice.controller;

import com.task.webservice.model.CreditCard;
import com.task.webservice.service.CreditCardService;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditProfileController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardService creditCardService;

    @RequestMapping(value = {"/creditProfile.html", "credit-profile.html"})
    public String viewProfile(Model model) {
        addCommonAttributes(userService, model);
        return "/credit-profile.html";
    }

    @RequestMapping(value = "/creditCardUpdate", method = RequestMethod.POST)
    public String profileUpdate(@ModelAttribute("creditCard") CreditCard creditCard) {
        creditCardService.updateCreditCardData(getCurrentUserName(), creditCard);
        return "redirect:/credit-profile.html";
    }

    @RequestMapping(value = "/creditCardDelete", method = RequestMethod.POST)
    public String profileDelete(@ModelAttribute("creditCard") CreditCard creditCard) {
        creditCardService.deleteCreditData(getCurrentUserName(), creditCard);
        return "redirect:/credit-profile.html";
    }
}
