package com.hibernateproject.entity;

public class Students {


    private int studId;
    private String studName;;
    private int studRollNumber;

    private Address addresses;

    public int getStudId() {
        return studId;
    }

    public void setStudId(int studId) {
        this.studId = studId;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public int getStudRollNumber() {
        return studRollNumber;
    }

    public void setStudRollNumber(int studRollNumber) {
        this.studRollNumber = studRollNumber;
    }

    public Address getAddresses() {
        return addresses;
    }

    public void setAddresses(Address addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studId=" + studId +
                ", studName='" + studName + '\'' +
                ", studRollNumber=" + studRollNumber +
                ", addresses=" + addresses +
                '}';
    }
}
