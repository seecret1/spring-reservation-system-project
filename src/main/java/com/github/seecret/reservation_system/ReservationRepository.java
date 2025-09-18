package com.github.seecret.reservation_system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

//    List<ReservationEntity> findAllByStatus(ReservationStatus status);
//
//    @Query(value = "select * from reservations r where r.status = :status", nativeQuery = true)
//    List<ReservationEntity> findAllByStatus(ReservationStatus status);
//
//    @Query("select r from ReservationEntity r where r.roomId = :roodId")
//    List<ReservationEntity> findAllByRodId(@Param("roomId") Long roodId);
//
//    @Transactional
//    @Modifying
//    @Query("update ReservationEntity r set r.userId = :userId, r.roomId = :roomId," +
//            " r.startDate = :startDate, r.endDate = :endDate, r.status = :status")
//    int updateAllFields(
//            @Param("id") Long id,
//            @Param("userId") Long userId,
//            @Param("roomId") Long roomId,
//            @Param("startDate")LocalDate startDate,
//            @Param("endDate") LocalDate endDate,
//            @Param("status") ReservationStatus status
//            );

    @Modifying
    @Query("update ReservationEntity r set r.status = :status where r.id = :id")
    void setStatus(
            @Param("id") Long id,
            @Param("status") ReservationStatus status);
}
