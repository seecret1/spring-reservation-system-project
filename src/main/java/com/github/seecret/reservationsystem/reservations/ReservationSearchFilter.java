package com.github.seecret.reservationsystem.reservations;

public record ReservationSearchFilter(

        Long roomId,

        Long userId,

        ReservationStatus status,

        Integer pageSize,

        Integer pageNumber
) {
}
