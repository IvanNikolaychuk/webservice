package com.task.webservice.controller.cofig;

import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import com.task.webservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(user);
    }
}
