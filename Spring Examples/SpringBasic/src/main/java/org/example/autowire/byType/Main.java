package org.example.autowire.byType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("autowireApplicationContext.xml");
        Car car = (Car) context.getBean("carBean1");
        car.display();
    }
}
