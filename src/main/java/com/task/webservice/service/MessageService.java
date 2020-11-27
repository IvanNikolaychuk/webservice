package com.task.webservice.service;

import com.task.webservice.model.Message;
import com.task.webservice.model.Profile;
import com.task.webservice.model.Role;
import com.task.webservice.model.User;
import com.task.webservice.repository.MessageRepository;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.task.webservice.model.Message.Status.SENT;

@Service
@Transactional
public class MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public MessageService() {
    }

    public void save(String userName, Message message) {
        User sender = userRepository.findByUsername(userName);

        if (sender != null && message != null) {
            User receiver = randomAdminUser();
            message.setStatus(SENT);
            message.setSenderId(sender.getId());
            message.setReceiverId(receiver.getId());

            if (sender.getMessages() == null) {
                sender.setMessages(new ArrayList<>());
            }

            sender.getMessages().add(message);
            userRepository.save(sender);
        }
    }

    public long numberOfUnreadMessages(String userName) {
        User user = userRepository.findByUsername(userName);

        if (user != null && user.getMessages() != null) {
            return user.getMessages()
                    .stream()
                    .filter(message -> message.getStatus() == Message.Status.UNREAD)
                    .count();
        }

        return 0;
    }

    public long numberOfReadMessages(String userName) {
        User user = userRepository.findByUsername(userName);

        if (user != null && user.getMessages() != null) {
            return user.getMessages()
                    .stream()
                    .filter(message -> message.getStatus() == Message.Status.READ)
                    .count();
        }

        return 0;
    }


    public long numberOfAnsweredMessagesForAdmin(String userName) {
        User user = userRepository.findByUsername(userName);

        if (user != null) {
            return findByReceiverId(user.getId())
                    .stream()
                    .filter(Message::isReplied)
                    .count();
        }

        return 0;
    }

    public long numberOfUnansweredMessagesForAdmin(String userName) {
        User user = userRepository.findByUsername(userName);

        if (user != null) {
            return findByReceiverId(user.getId())
                    .stream()
                    .filter(message -> !message.isReplied())
                    .count();
        }

        return 0;
    }

    private User randomAdminUser() {
        List<User> admins = userRepository.findByRole(Role.ROLE_ADMIN.name());
        Collections.shuffle(admins);
        return admins.get(0);
    }

    List<Message> findByReceiverId(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

}
