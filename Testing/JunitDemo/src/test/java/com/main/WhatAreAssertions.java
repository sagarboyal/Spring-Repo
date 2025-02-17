package com.main;

import org.junit.jupiter.api.Test;

class WhatAreAssertions {

    @Test
    public void assertAlternative(){
        JavaOperation javaOperations = new JavaOperation();
        if (javaOperations.add(4,5) == 8)
            System.out.println("Test passed");
        else
            System.out.println("Test failed");
    }
}