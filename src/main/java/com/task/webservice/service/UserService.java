package com.task.webservice.service;

import com.task.webservice.model.Profile;
import com.task.webservice.model.User;
import com.task.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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

    public void updateProfileData(String userName, Profile updatedProfile) {
        User userFromDb = userRepository.findByUsername(userName);

        if (userFromDb != null && updatedProfile != null) {
            updateProfile(userFromDb, updatedProfile);
            userRepository.save(userFromDb);
        }
    }

    private void updateProfile(User userFromDb, Profile updatedProfile) {

        Optional<Profile> profileOptional = findById(updatedProfile.getId(), userFromDb);

        if (updatedProfile.isDefaultBillingAddress()) {
            userFromDb.getProfiles().forEach(profile -> profile.setDefaultBillingAddress(false));
        }
        if (updatedProfile.isDefaultShippingAddress()) {
            userFromDb.getProfiles().forEach(profile -> profile.setDefaultShippingAddress(false));
        }

        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setAddress(updatedProfile.getAddress());
            profile.setPostalCode(updatedProfile.getPostalCode());
            profile.setDefaultBillingAddress(updatedProfile.isDefaultBillingAddress());
            profile.setDefaultShippingAddress(updatedProfile.isDefaultShippingAddress());
        } else {
            userFromDb.getProfiles().add(updatedProfile);
        }
    }

    public void deleteProfileData(String userName, Profile updatedProfile) {
        User userFromDb = userRepository.findByUsername(userName);
        if (userFromDb != null && updatedProfile != null && updatedProfile.getId() != null) {
            Optional<Profile> profileToBeDeleted = findById(updatedProfile.getId(), userFromDb);
            profileToBeDeleted.ifPresent(profile -> userFromDb.getProfiles().remove(profile));
            profileToBeDeleted.ifPresent(oldProfile -> {
                if (oldProfile.isDefaultShippingAddress()) {
                    if (!userFromDb.getProfiles().isEmpty()) {
                        userFromDb.getProfiles().get(0).setDefaultShippingAddress(true);
                    }
                }
                if (oldProfile.isDefaultBillingAddress()) {
                    if (!userFromDb.getProfiles().isEmpty()) {
                        userFromDb.getProfiles().get(0).setDefaultBillingAddress(true);
                    }
                }
            });
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
    }

    public Optional<Profile> findById(Long profileId, User user) {
        return user.getProfiles()
                .stream()
                .filter(profile -> profile.getId().equals(profileId))
                .findFirst();
    }

}
