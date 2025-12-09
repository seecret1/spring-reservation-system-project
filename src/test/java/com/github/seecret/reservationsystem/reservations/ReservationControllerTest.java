package com.github.seecret.reservationsystem.reservations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private ReservationController controller;


    @Test
    void updateReservation() {
        Long id = 19L;

        Random random = new Random();

        Reservation reservation = new Reservation(random.nextLong(), random.nextLong(), random.nextLong(),
                LocalDate.parse("2025-04-04"), LocalDate.parse("2025-06-06"), ReservationStatus.PENDING);

        assertEquals(expected, controller.updateReservation(id, reservation));
    }

    @Test
    void getReservations() {
        Long id = 20L;

        when(repository.existsById(id)).thenReturn(false);

        String expected = "No such row";

        assertEquals(expected, controller.getReservationById(id));
    }
}