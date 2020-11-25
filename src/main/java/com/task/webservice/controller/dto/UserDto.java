package com.task.webservice.controller.dto;

import com.task.webservice.model.User;

public class UserDto {
    public String firstName;
    public String lastName;
    public String address;
    public String password;
    public String email;

    public UserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.password = user.getPassword();
        this.email = user.getUsername();
    }
}
