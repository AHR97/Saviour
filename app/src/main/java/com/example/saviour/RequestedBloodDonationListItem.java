package com.example.saviour;

public class RequestedBloodDonationListItem {

    private  String City;
    private  String Area;
    private  String ContactNumber;
    private  String NumberOfBags;
    private String BloodGroup;
    private  String Hospital;
    private String UserName;
    private  String Flag;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public RequestedBloodDonationListItem() {
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

    public String getNumberOfBags() {
        return NumberOfBags;
    }

    public void setNumberOfBags(String numberOfBags) {
        NumberOfBags = numberOfBags;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public RequestedBloodDonationListItem(String city, String area, String numberOfBags, String bloodGroup, String hospital) {
        City = city;
        Area = area;
        NumberOfBags = numberOfBags;
        BloodGroup = bloodGroup;
        Hospital = hospital;
    }

    public RequestedBloodDonationListItem(String city, String area, String contactNumber, String numberOfBags, String bloodGroup, String hospital, String userName) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        NumberOfBags = numberOfBags;
        BloodGroup = bloodGroup;
        Hospital = hospital;
        UserName = userName;
    }
}
