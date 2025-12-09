package com.github.seecret.reservationsystem.reservations.availabittity;

public record CheckAvailabilityResponse(
        String message,

        AvailabilityStatus status
) {
}
