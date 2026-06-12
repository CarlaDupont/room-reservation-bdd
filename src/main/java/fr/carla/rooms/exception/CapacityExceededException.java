package fr.carla.rooms.exception;

public class CapacityExceededException extends RuntimeException {

    public CapacityExceededException(int participants, int maxCapacity) {
        super(
                "Capacity exceeded: participants = "
                        + participants
                        + ", max capacity = "
                        + maxCapacity
        );
    }
}