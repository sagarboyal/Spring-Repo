package org.example;

import org.example.bean.MyBean;
import org.example.dependencyInjection.constructor.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context
                = new ClassPathXmlApplicationContext("applicationContext.xml");

        MyBean myBean = (MyBean) context.getBean("myBean");
        System.out.println(myBean); // MyBean{message='Hello, Spring default value from XML!'}

        myBean.setMessage("Hello World");
        System.out.println(myBean); // MyBean{message='Hello World'}

        // constructor injection
        Car car = (Car) context.getBean("carBeanForConstructor");
        car.display();

        // setter injection
        org.example.dependencyInjection.setter.Car car2 = (org.example.dependencyInjection.setter.Car) context.getBean("carBeanForSetter");
        car2.display();

    }
}