package com.hibernateaggregation.aggregation;

public class main {

    public static void main(String[] args) {

        Engine engine = new Engine();
        engine.setEngineOIlUsage(10.0f);
     Car car =new Car();
     car.setEngine(engine);
     car.setColor("Red");
     car.setMilleage(100);

        System.out.println(car.getEngine().getEngineOIlUsage());
        System.out.println(car.getColor());





    }
}
