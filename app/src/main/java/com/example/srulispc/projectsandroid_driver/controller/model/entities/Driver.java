package com.example.srulispc.projectsandroid_driver.controller.model.entities;

public class Driver {

    private int id;
    private String firstName;
    private String lastName;
    private String mail;
    private int phoneNumber;
    private long creditCard;

    public CustomLocation getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(CustomLocation myLocation) {
        this.myLocation = myLocation;
    }

    private CustomLocation myLocation;

    public Driver(){}
    public Driver(int id, String firstName, String lastName, String mail, int phoneNumber, long creditCard) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.creditCard = creditCard;
    }

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
