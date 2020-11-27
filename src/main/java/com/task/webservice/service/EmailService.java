package com.task.webservice.service;

import com.task.webservice.model.User;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class EmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    public void sendEmailUponUserRegistration(User user) {
        LOGGER.info("Sending registration email to: " + user.getUsername());
    }
}
