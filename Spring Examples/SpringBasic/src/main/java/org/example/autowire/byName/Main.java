package org.example.autowire.byName;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("autowireApplicationContext.xml");
        Car car = (Car) context.getBean("carBean");
        car.display();
    }
}
