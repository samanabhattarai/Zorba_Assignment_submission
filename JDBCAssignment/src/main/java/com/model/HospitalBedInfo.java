package com.model;

import java.util.Scanner;

public class HospitalBedInfo {

    private String bedId;
    private String bedType;
    private Double bedChargesRate;
    private int hospitalId;;

    public HospitalBedInfo(String bedId, String bedType, Double bedChargesRate, int hospitalId) {
        this.bedId=bedId;
        this.bedType=bedType;
        this.bedChargesRate=bedChargesRate;
        this.hospitalId=hospitalId;
    }

    public String getBedId() {
        return bedId;
    }



    public String getBedType() {
        return bedType;
    }



    public Double getBedChargesRate() {
        return bedChargesRate;
    }


    public int getHospitalId() {
        return hospitalId;
    }



    @Override
    public String toString() {
        return "HospitalBedInfo{" +
                "bedId=" + bedId +
                ", bedType='" + bedType + '\'' +
                ", bedChargesRate=" + bedChargesRate +
                ", hospitalId=" + hospitalId +
                '}';
    }
}
