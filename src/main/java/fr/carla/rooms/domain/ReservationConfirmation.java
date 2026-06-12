package fr.carla.rooms.domain;

public class ReservationConfirmation {

    private final String roomCode;
    private final String userEmail;
    private final String message;

    public ReservationConfirmation(
            String roomCode,
            String userEmail,
            String message
    ) {
        this.roomCode = roomCode;
        this.userEmail = userEmail;
        this.message = message;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getMessage() {
        return message;
    }
}