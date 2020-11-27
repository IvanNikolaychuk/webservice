package com.task.webservice.service;

import com.task.webservice.model.Profile;
import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public void saveNewUser(User user) {
        if (user.getProfiles() == null) {
            user.setProfiles(new ArrayList<>());
        }

        user.getProfiles().add(Profile.defaultFor(user));
        userRepository.save(user);
    }

    public void updateUserDate(String userName, User updatedUser) {
        User userFromDb = userRepository.findByUsername(userName);

        if (userFromDb != null) {
            updateUserDetails(userFromDb, updatedUser);
            userRepository.save(userFromDb);
        }
    }

    private void updateUserDetails(User userFromDb, User updatedUser) {
        if (updatedUser.getFirstName() != null) {
            userFromDb.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            userFromDb.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getBirthday() != null) {
            userFromDb.setBirthday(updatedUser.getBirthday());
        }
        if (updatedUser.getUsername() != null) {
            userFromDb.setBirthday(updatedUser.getUsername());
        }
    }

    public void recordLogin(String userName) {
        User userFromDb = userRepository.findByUsername(userName);

        if (userFromDb != null) {
            userFromDb.recordLogin();
            userRepository.save(userFromDb);
        }
    }

    public void recordProfileUpdate(String userName) {
        User userFromDb = userRepository.findByUsername(userName);

        if (userFromDb != null) {
            userFromDb.recordProfileUpdate();
            userRepository.save(userFromDb);
        }
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }
}
