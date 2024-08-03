package com.model;

import java.util.Scanner;

public class Patient {
    private int patientId;
    private String patientName;
    private String patientType;
    private int noOfDays;
    int totalNoOfBedCharges;


    public Patient(int patientId, String patientName, String patientType, int noOfDays) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientType = patientType;
        this.noOfDays = noOfDays;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientType() {
        return patientType;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public int getTotalNoOfBedCharges() {
        return totalNoOfBedCharges;
    }

    public void setTotalNoOfBedCharges(int totalNoOfBedCharges) {
        this.totalNoOfBedCharges = totalNoOfBedCharges;
    }
}
