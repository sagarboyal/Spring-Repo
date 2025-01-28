package org.example.dependencyInjection.constructor;

public class Car {
    private final Specifications specifications;

    public Car(Specifications specifications) {
        this.specifications = specifications;
    }

    public void display() {
        System.out.println("Car specifications: " + specifications);
    }
}
