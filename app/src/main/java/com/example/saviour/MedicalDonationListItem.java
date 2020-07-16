package com.example.saviour;

public class MedicalDonationListItem {
    private String Name;
    private String Details;
    private String ContactNumber;
    private String BKashNumber;
    private String TotalAmount;
    private String UserName;
    private String CollectedAmount;
    private String Uid;

    public MedicalDonationListItem(String name, String details, String contactNumber, String BKashNumber, String totalAmount, String userName, String collectedAmount) {
        Name = name;
        Details = details;
        ContactNumber = contactNumber;
        this.BKashNumber = BKashNumber;
        TotalAmount = totalAmount;
        UserName = userName;
        CollectedAmount = collectedAmount;
    }

    public MedicalDonationListItem()
    {

    }

    public MedicalDonationListItem(String name, String details, String contactNumber, String bKashNumber, String totalAmount, String userName) {
        Name = name;
        Details = details;
        ContactNumber = contactNumber;
        BKashNumber = bKashNumber;
        TotalAmount = totalAmount;
        UserName = userName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
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

    public String getbKashNumber() {
        return BKashNumber;
    }

    public void setbKashNumber(String BKashNumber) {
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

    public String getCollectedAmount() {
        return CollectedAmount;
    }

    public void setCollectedAmount(String collectedAmount) {
        CollectedAmount = collectedAmount;
    }

    public MedicalDonationListItem(String name, String details, String contactNumber, String totalAmount) {
        Name = name;
        Details=details;
        ContactNumber = contactNumber;
        TotalAmount = totalAmount;
    }
}
