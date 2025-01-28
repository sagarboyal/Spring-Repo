package org.example.autowire.byConstructor;

public class Car {

    private Specifications specifications;

    public Car(Specifications specifications1) {
        this.specifications = specifications1;
    }

    public void display() {
        System.out.println("Car specifications: " + specifications);
    }
}
