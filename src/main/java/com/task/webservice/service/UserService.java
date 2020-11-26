package com.task.webservice.service;

import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
        super();
    }

    public User get(final String email) throws UsernameNotFoundException {
        return userRepository.findByUsername(email);
    }
}
