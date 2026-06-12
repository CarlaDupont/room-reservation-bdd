package fr.carla.rooms.repository;

import fr.carla.rooms.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> findByRoomCode(String roomCode);
}