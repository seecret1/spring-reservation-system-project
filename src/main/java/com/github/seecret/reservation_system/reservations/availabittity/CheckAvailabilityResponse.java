package com.github.seecret.reservation_system.reservations.availabittity;

public record CheckAvailabilityResponse(
        String message,

        AvailabilityStatus status
) {
}
