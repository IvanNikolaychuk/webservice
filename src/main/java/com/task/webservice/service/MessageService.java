package com.task.webservice.service;

import com.task.webservice.model.Message;
import com.task.webservice.model.Profile;
import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    public MessageService() {
    }

    public void save(String userName, Message message) {
        User sender = userRepository.findByUsername(userName);

        if (sender != null && message != null) {
            User receiver = randomAdminUser();
            message.setRead(false);
            message.setSenderId(sender.getId());
            message.setReceiverId(receiver.getId());

            if (sender.getMessages() == null) {
                sender.setMessages(new ArrayList<>());
            }

            sender.getMessages().add(message);
            userRepository.save(sender);
        }
    }

    private User randomAdminUser() {
        List<User> admins = userRepository.findByRole("ADMIN");
        Collections.shuffle(admins);
        return admins.get(0);
    }
}
