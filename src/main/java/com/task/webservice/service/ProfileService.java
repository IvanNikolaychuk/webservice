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
public class ProfileService {

    @Autowired
    private UserRepository userRepository;

    public ProfileService() {
        super();
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

    public Optional<Profile> findById(Long profileId, User user) {
        return user.getProfiles()
                .stream()
                .filter(profile -> profile.getId().equals(profileId))
                .findFirst();
    }

}
