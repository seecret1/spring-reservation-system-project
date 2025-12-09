package com.github.seecret.reservationsystem.reservations;

import com.github.seecret.reservationsystem.reservations.availabittity.ReservationAvailabilityService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;

    private final ReservationMapper mapper;

    private final ReservationAvailabilityService availabilityService;

    @Value("${reservation.page-size}")
    private int defPageSize;

    @Value("${reservation.page-number}")
    private int defPageNum;

    public Reservation getReservationById(
            Long id
    ) {

        ReservationEntity reservationEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Not found reservation by id: " + id
                ));

        return mapper.toDomain(reservationEntity);
    }

    public List<Reservation> searchAllByFilter(
            ReservationSearchFilter filter
    ) {
        int pageSize = filter.pageSize() != null
                ? filter.pageSize() : defPageSize;
        int pageNumber = filter.pageNumber() != null
                ? filter.pageNumber() : defPageNum;

        var pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        List<ReservationEntity> allEntities = repository.searchAllByFilter(
                filter.roomId(),
                filter.userId(),
                filter.status(),
                pageable
        );

        return allEntities.stream()
                .map(mapper::toDomain)
                .toList();
    }

    public Reservation createReservation(Reservation reservationToCreate) {
        if (reservationToCreate.status() != null) {
            throw new IllegalArgumentException("Status should be empty");
        }

        if (reservationToCreate.endDate().isBefore(reservationToCreate.startDate())) {
            throw new IllegalArgumentException("start date must be 1 day earlier than end date");
        }

        var entityToSave = mapper.toEntity(reservationToCreate);
        entityToSave.setStatus(ReservationStatus.PENDING);

        var savedEntity = repository.save(entityToSave);
        return mapper.toDomain(savedEntity);
    }

    public Reservation updateReservation(
            Long id,
            Reservation reservationToUpdate
    ) {
        var reservationEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));

        if (reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot modify reservation: status=" + reservationEntity.getStatus());
        }

        if (reservationToUpdate.endDate().isBefore(reservationToUpdate.startDate())) {
            throw new IllegalArgumentException("start date must be 1 day earlier than end date");
        }

        var reservationToSave = mapper.toEntity(reservationToUpdate);
        reservationToSave.setId(reservationEntity.getId());
        reservationToSave.setStatus(ReservationStatus.PENDING);

        var updatedReservation = repository.save(reservationToSave);

        return mapper.toDomain(updatedReservation);
    }

    @Transactional
    public void cancelReservation(Long id) {
        var reservation = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));

        if (reservation.getStatus().equals(ReservationStatus.APPROVED)) {
            throw new EntityNotFoundException("Cannot cancel approved reservation. Contact with manager please");
        }

        if (reservation.getStatus().equals(ReservationStatus.CANCELLED)) {
            throw new IllegalArgumentException("Cannot cancel the reservation. Reservation wa already cancelled");
        }

        repository.setStatus(id, ReservationStatus.CANCELLED);
        log.info("Successfully cancelled reservation: id = {}", id);
    }

    public Reservation approveReservation(Long id) {
        var reservationEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));

        if (reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Cannot approve reservation: status=" + reservationEntity.getStatus());
        }
        if (!availabilityService.isReservationAvailable(
                reservationEntity.getRoomId(),
                reservationEntity.getStartDate(),
                reservationEntity.getEndDate()
        )) {
            throw new IllegalStateException("Cannot approve reservation because of conflict");
        }

        reservationEntity.setStatus(ReservationStatus.APPROVED);
        repository.save(reservationEntity);

        return mapper.toDomain(reservationEntity);
    }
}