package fr.carla.rooms.service;

import fr.carla.rooms.domain.Reservation;
import fr.carla.rooms.domain.ReservationConfirmation;
import fr.carla.rooms.domain.Room;
import fr.carla.rooms.exception.CapacityExceededException;
import fr.carla.rooms.exception.InvalidReservationPeriodException;
import fr.carla.rooms.exception.RoomAlreadyBookedException;
import fr.carla.rooms.exception.RoomNotFoundException;
import fr.carla.rooms.notification.NotificationService;
import fr.carla.rooms.repository.ReservationRepository;
import fr.carla.rooms.repository.RoomRepository;

import java.util.List;

public class RoomReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;

    public RoomReservationService(
            RoomRepository roomRepository,
            ReservationRepository reservationRepository,
            NotificationService notificationService
    ) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
    }

    public ReservationConfirmation reserve(Reservation reservation) {
        Room room = roomRepository
                .findByCode(reservation.getRoomCode())
                .orElseThrow(() ->
                        new RoomNotFoundException(
                                reservation.getRoomCode()
                        )
                );

        if (reservation.getParticipants() > room.getMaxCapacity()) {
            throw new CapacityExceededException(
                    reservation.getParticipants(),
                    room.getMaxCapacity()
            );
        }

        if (!reservation.getEndDate().isAfter(reservation.getStartDate())) {
            throw new InvalidReservationPeriodException();
        }

        List<Reservation> existingReservations =
                reservationRepository.findByRoomCode(
                        reservation.getRoomCode()
                );

        boolean hasConflict = existingReservations
                .stream()
                .anyMatch(existingReservation ->
                        overlaps(reservation, existingReservation)
                );

        if (hasConflict) {
            throw new RoomAlreadyBookedException(
                    reservation.getRoomCode()
            );
        }

        ReservationConfirmation confirmation =
                new ReservationConfirmation(
                        reservation.getRoomCode(),
                        reservation.getUserEmail(),
                        "Reservation accepted for "
                                + reservation.getUserEmail()
                );

        notificationService.sendConfirmation(confirmation);

        return confirmation;
    }

    private boolean overlaps(
            Reservation requestedReservation,
            Reservation existingReservation
    ) {
        return requestedReservation.getStartDate()
                .isBefore(existingReservation.getEndDate())
                && requestedReservation.getEndDate()
                .isAfter(existingReservation.getStartDate());
    }
}