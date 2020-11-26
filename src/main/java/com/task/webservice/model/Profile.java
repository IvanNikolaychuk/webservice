package com.task.webservice.model;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String address;
    private String postalCode;

    boolean defaultBillingAddress;
    boolean defaultShippingAddress;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public void setDefaultBillingAddress(boolean defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
    }

    public boolean isDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(boolean defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }
}
