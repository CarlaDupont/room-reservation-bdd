package fr.carla.rooms.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(String roomCode) {
        super("Unknown room: " + roomCode);
    }
}