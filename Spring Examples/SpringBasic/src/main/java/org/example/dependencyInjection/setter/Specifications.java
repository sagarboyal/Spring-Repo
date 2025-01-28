package org.example.dependencyInjection.setter;

public class Specifications {
    private String make;
    private String model;

    public String getMake() {
        return make;
    }
    public void setMake(String make) {
        this.make = make;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Specifications{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
