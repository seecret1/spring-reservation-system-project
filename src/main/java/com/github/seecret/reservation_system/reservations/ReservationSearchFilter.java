package com.github.seecret.reservation_system.reservations;

public record ReservationSearchFilter(
        Long roomId,

        Long userId,

        Integer pageSize,

        Integer pageNumber
) {
}
