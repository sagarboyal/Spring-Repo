package org.example.autowire.byType;

public class Car {

    private Specifications specifications;

    public void setSpecifications(Specifications specifications) {
        this.specifications = specifications;
    }

    public void display() {
        System.out.println("Car specifications: " + specifications);
    }
}
