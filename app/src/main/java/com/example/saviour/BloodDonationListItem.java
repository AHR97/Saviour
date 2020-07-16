package com.example.saviour;

public class BloodDonationListItem {

    private  String City;
    private  String Area;
    private  String ContactNumber;
    private  String NumberOfBags;
    private String BloodGroup;
    private  String Hospital;
    private String UserName;
    private String Uid;

    private  String Flag;

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public BloodDonationListItem()
    {

    }

    public BloodDonationListItem(String city, String area, String contactNumber, String numberOfBags, String bloodGroup, String hospital, String userName, String flag) {
        City = city;
        Area = area;
        ContactNumber = contactNumber;
        NumberOfBags = numberOfBags;
        BloodGroup = bloodGroup;
        Hospital = hospital;
        UserName = userName;
        Flag=flag;
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

    public BloodDonationListItem(String bloodGroup, String userName, String contactNumber) {
        ContactNumber = contactNumber;
        BloodGroup = bloodGroup;
        UserName = userName;
    }


}
