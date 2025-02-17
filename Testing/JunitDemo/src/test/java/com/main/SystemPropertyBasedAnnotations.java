package com.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

class SystemPropertyBasedAnnotations {

    /*
    @EnabledIfSystemProperty
    @DisabledIfSystemProperty
    */

    /*@Test
    public void printSystemProperty(){
        System.out.println(System.getProperties());
    }*/

    @Test
    @EnabledIfSystemProperty(named = "java.specification.version", matches = "13")
    public void getSystemProperty() {
        System.out.println(System.getProperty("java.specification.version"));
    }

    @Test
    @DisabledIfSystemProperty(named = "user.country", matches = "US")
    public void getSystemPropertyCountry() {
        System.out.println(System.getProperty("user.country"));
    }


}