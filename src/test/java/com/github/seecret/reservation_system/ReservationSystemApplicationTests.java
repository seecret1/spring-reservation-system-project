package com.github.seecret.reservation_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
