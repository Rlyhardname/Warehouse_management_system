package com.example.warehouses.Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientFunctionalityControllerTest {

    @Test
    void visualizeMainPageHelloWorld() {
        assertEquals("Hello World", ClientFunctionalityController.visualizeMainPage());
    }
    @Test
    void visualizeMainPageCharacterLengthEqualsEleven(){
        assertEquals(11,ClientFunctionalityController.visualizeMainPage().length());
    }

}