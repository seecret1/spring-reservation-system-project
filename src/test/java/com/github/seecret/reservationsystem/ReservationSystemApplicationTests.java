package com.github.seecret.reservationsystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class ReservationSystemApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void concatTest() {
        String stringOne = "one ";
        String stringTwo = "two";

        assertEquals("one two", stringOne + stringTwo);
    }
}
