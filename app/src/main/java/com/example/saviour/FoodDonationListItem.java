package com.example.saviour;

public class FoodDonationListItem {

    private String City;
    private String Area;
    private String ContactNumber;
    private String Place;
    private String Reason;
    private String UserName;
    private String NumberOfPeople;
    private String Uid;
    private  String Flag;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
    public FoodDonationListItem(String city, String area, String contactNumber, String place, String reason, String userName, String numberOfPeople) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        Place = place;
        Reason = reason;
        UserName = userName;
        NumberOfPeople = numberOfPeople;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public FoodDonationListItem() {
    }


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNumberOfPeople() {
        return NumberOfPeople;
    }

    public void setNumberOfPeople(String numberOfPeople) {
        NumberOfPeople = numberOfPeople;
    }

    public FoodDonationListItem(String city, String area, String contactNumber, String reason, String userName, String numberOfPeople) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        Reason = reason;
        UserName = userName;
        NumberOfPeople = numberOfPeople;
    }
}
