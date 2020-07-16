package com.example.saviour;

public class DonatedEventListItem {
    private String Name;
    private String Details;
    private String ContactNumber;
    private String BKashNumber;
    private String TotalAmount;
    private String UserName;
    private String GivenAmount;

    public DonatedEventListItem() {
    }

    public DonatedEventListItem(String name, String details, String contactNumber, String BKashNumber, String totalAmount, String userName, String givenAmount) {
        Name = name;
        Details = details;
        ContactNumber = contactNumber;
        this.BKashNumber = BKashNumber;
        TotalAmount = totalAmount;
        UserName = userName;
        GivenAmount = givenAmount;
    }

    public DonatedEventListItem(String name, String details, String totalAmount, String userName, String givenAmount) {
        Name = name;
        Details = details;
        TotalAmount = totalAmount;
        UserName = userName;
        GivenAmount = givenAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getBKashNumber() {
        return BKashNumber;
    }

    public void setBKashNumber(String BKashNumber) {
        this.BKashNumber = BKashNumber;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getGivenAmount() {
        return GivenAmount;
    }

    public void setGivenAmount(String givenAmount) {
        GivenAmount = givenAmount;
    }
}
