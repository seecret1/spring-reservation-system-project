package com.github.seecret.reservation_system.reservations;

public record ReservationSearchFilter(
        Long roomId,

        Long userId,

        ReservationStatus status,

        Integer pageSize,

        Integer pageNumber
) {
}
