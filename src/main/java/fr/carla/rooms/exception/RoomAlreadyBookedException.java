package fr.carla.rooms.exception;

public class RoomAlreadyBookedException extends RuntimeException {

    public RoomAlreadyBookedException(String roomCode) {
        super("Room already booked: " + roomCode);
    }
}