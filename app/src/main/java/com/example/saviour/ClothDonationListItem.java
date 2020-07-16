package com.example.saviour;

public class ClothDonationListItem {

    private String City;
    private String Area;
    private String ContactNumber;
    private String Place;
    private String Category;
    private String UserName;
    private String Uid;
    private  String Flag;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }
    public ClothDonationListItem() {
    }

    public ClothDonationListItem(String city, String area, String contactNumber, String place, String category, String userName) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        Place = place;
        Category = category;
        UserName = userName;
    }

    public ClothDonationListItem(String city, String area, String contactNumber, String category, String userName) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        Category = category;
        UserName = userName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
