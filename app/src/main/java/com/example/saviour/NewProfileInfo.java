package com.example.saviour;

public class NewProfileInfo {
    private String UserName;
    private String FullName;
    private String Email;
    private String PhoneNumber;
    private String BloodGroup;
    private String Address;
    private String Age;
    private String bKashNumber;
    private String ProfileImageUrl;


    public NewProfileInfo() {
    }

    public NewProfileInfo(String userName, String fullName, String email, String phoneNumber, String bloodGroup, String address, String age, String bKashNumber, String profileImageUrl) {
        UserName = userName;
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        BloodGroup = bloodGroup;
        Address = address;
        Age = age;
        this.bKashNumber = bKashNumber;
        ProfileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrl() {
        return ProfileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        ProfileImageUrl = profileImageUrl;
    }

    public NewProfileInfo(String userName, String fullName, String email, String phoneNumber, String bloodGroup, String address, String age, String bKashNumber) {
        UserName = userName;
        FullName = fullName;
        Email = email;
        PhoneNumber = phoneNumber;
        BloodGroup = bloodGroup;
        Address = address;
        Age = age;
        this.bKashNumber = bKashNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getbKashNumber() {
        return bKashNumber;
    }

    public void setbKashNumber(String bKashNumber) {
        this.bKashNumber = bKashNumber;
    }
}
