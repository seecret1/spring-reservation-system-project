package com.github.seecret.reservation_system.reservations.availabittity;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reservation/availability")
@RequiredArgsConstructor
public class ReservationAvailabilityController {

    private final ReservationAvailabilityService service;

    @PostMapping("/check")
    public ResponseEntity<CheckAvailabilityResponse> checkAvailability(
            @Valid CheckAvailabilityRequest request
    ) {
        log.info("Called method checkAvailability, request = {}", request);

        boolean isAvailable = service.isReservationAvailable(
                request.roomId(), request.startDate(), request.endDate()
        );

        var message = isAvailable
                ? "Room available to reservation"
                : "Room not available to reservation";

        var status = isAvailable
                ? AvailabilityStatus.AVAILABLE
                : AvailabilityStatus.RESERVED;
        return ResponseEntity.status(200)
                .body(new CheckAvailabilityResponse(message, status));
    }
}
