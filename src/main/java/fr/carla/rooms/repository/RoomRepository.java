package fr.carla.rooms.repository;

import fr.carla.rooms.domain.Room;

import java.util.Optional;

public interface RoomRepository {

    Optional<Room> findByCode(String code);
}