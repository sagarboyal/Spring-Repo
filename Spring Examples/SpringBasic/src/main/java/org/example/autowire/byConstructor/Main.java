package org.example.autowire.byConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("autowireApplicationContext.xml");
        Car car = (Car) context.getBean("carBean2");
        car.display();
    }
}
