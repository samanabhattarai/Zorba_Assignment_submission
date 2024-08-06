package com.hibernateaggregation.entity;

import java.util.Set;

public class Student {
    private int studId;
    private String studName;;
    private int studRollNumber;

    Set<Subject> subject;

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

    public Set<Subject> getSubject() {
        return subject;
    }

    public void setSubject(Set<Subject> subject) {
        this.subject = subject;
    }
}
