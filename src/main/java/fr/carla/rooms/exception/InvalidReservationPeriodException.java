package fr.carla.rooms.exception;

public class InvalidReservationPeriodException extends RuntimeException {

    public InvalidReservationPeriodException() {
        super("Invalid reservation period");
    }
}