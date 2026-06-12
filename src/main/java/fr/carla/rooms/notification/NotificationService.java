package fr.carla.rooms.notification;

import fr.carla.rooms.domain.ReservationConfirmation;

public interface NotificationService {

    void sendConfirmation(ReservationConfirmation confirmation);
}