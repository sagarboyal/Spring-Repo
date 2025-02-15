package com.main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JavaOperationTest {

    JavaOperation javaOperation;

    @BeforeEach // allow you to call this method before calling every test case
    public void init(){
        javaOperation = new JavaOperation();
        System.out.println("JavaOperation init");
    }
    @AfterEach // allow you to call this method after calling every test case
    public void cleanUp(){
        System.out.println("cleanUp done");
    }

    @Test
    void addTest(){
        System.out.println("addTest");
    }
    @Test
    void multiplyTest(){
        System.out.println("multiplyTest");
    }
}