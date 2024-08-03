package com.model;

public class Hospital {
    private int hospitalId;
    private String hospitalName;

    public Hospital(int hospitalId, String hospitalName) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
