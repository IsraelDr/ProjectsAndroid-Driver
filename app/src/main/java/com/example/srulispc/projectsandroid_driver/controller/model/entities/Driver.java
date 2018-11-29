package com.example.srulispc.projectsandroid_driver.controller.model.entities;

public class Driver {

    private int id;
    private String firstName;
    private String lastName;
    private String mail;
    private int phoneNumber;
    private long creditCard;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getCreditCard() {
        return creditCard;
    }
    public void setCreditCard(long creditCard) {
        this.creditCard = creditCard;
    }
}
