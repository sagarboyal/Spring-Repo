package org.example;

import org.example.bean.MyBean;
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
    }
}