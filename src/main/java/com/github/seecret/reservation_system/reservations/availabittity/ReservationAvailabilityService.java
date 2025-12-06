package com.github.seecret.reservation_system.reservations.availabittity;

import com.github.seecret.reservation_system.reservations.ReservationRepository;
import com.github.seecret.reservation_system.reservations.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationAvailabilityService {

    private final ReservationRepository repository;

    public boolean isReservationAvailable(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("start date must be 1 day earlier than end date");
        }

        List<Long> conflictingIds = repository
                .findConflictReservationIds(
                        roomId,
                        startDate,
                        endDate,
                        ReservationStatus.APPROVED
                );

        if (conflictingIds.isEmpty()) {
            return true;
        }

        log.info("Conflict with ids = {}", conflictingIds);
        return false;
    }
}
