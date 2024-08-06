package com.hibernateaggregation.aggregation;

public class Car {
    private int milleage;
    private String color;
    private Engine engine;

    public int getMilleage() {
        return milleage;
    }
    public void setMilleage(int milleage) {
        this.milleage = milleage;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Engine getEngine() {
        return engine;
    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
