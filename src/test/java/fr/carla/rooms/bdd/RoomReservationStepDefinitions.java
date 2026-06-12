package fr.carla.rooms.bdd;

import fr.carla.rooms.domain.Reservation;
import fr.carla.rooms.domain.ReservationConfirmation;
import fr.carla.rooms.domain.Room;
import fr.carla.rooms.notification.NotificationService;
import fr.carla.rooms.repository.ReservationRepository;
import fr.carla.rooms.repository.RoomRepository;
import fr.carla.rooms.service.RoomReservationService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoomReservationStepDefinitions {

    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;
    private NotificationService notificationService;
    private RoomReservationService reservationService;

    private ReservationConfirmation confirmation;
    private RuntimeException caughtException;

    @Before
    public void setUp() {
        roomRepository = mock(RoomRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        notificationService = mock(NotificationService.class);

        reservationService = new RoomReservationService(
                roomRepository,
                reservationRepository,
                notificationService
        );

        confirmation = null;
        caughtException = null;
    }

    @Given(
            "a room with code {string}, name {string}, and maximum capacity {int}"
    )
    public void aRoomExists(
            String code,
            String name,
            int maxCapacity
    ) {
        Room room = new Room(code, name, maxCapacity);

        when(roomRepository.findByCode(code))
                .thenReturn(Optional.of(room));
    }

    @Given("the room with code {string} does not exist")
    public void theRoomDoesNotExist(String code) {
        when(roomRepository.findByCode(code))
                .thenReturn(Optional.empty());
    }

    @Given("no existing reservation exists for room {string}")
    public void noExistingReservationExistsForRoom(String roomCode) {
        when(reservationRepository.findByRoomCode(roomCode))
                .thenReturn(List.of());
    }

    @Given(
            "an existing reservation for room {string} from {string} to {string}"
    )
    public void anExistingReservationForRoom(
            String roomCode,
            String startDate,
            String endDate
    ) {
        Reservation existingReservation = new Reservation(
                "existing@company.com",
                roomCode,
                3,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate)
        );

        when(reservationRepository.findByRoomCode(roomCode))
                .thenReturn(List.of(existingReservation));
    }

    @When(
            "the user {string} reserves room {string} for {int} participants from {string} to {string}"
    )
    public void theUserReservesRoom(
            String email,
            String roomCode,
            int participants,
            String startDate,
            String endDate
    ) {
        Reservation reservation = new Reservation(
                email,
                roomCode,
                participants,
                LocalDateTime.parse(startDate),
                LocalDateTime.parse(endDate)
        );

        try {
            confirmation = reservationService.reserve(reservation);
        } catch (RuntimeException exception) {
            caughtException = exception;
        }
    }

    @Then("the reservation is accepted")
    public void theReservationIsAccepted() {
        assertNull(caughtException);
        assertNotNull(confirmation);
    }

    @Then("the reservation is rejected")
    public void theReservationIsRejected() {
        assertNotNull(caughtException);
        assertNull(confirmation);
    }

    @Then("the confirmation contains room code {string}")
    public void theConfirmationContainsRoomCode(String expectedRoomCode) {
        assertNotNull(confirmation);

        assertEquals(
                expectedRoomCode,
                confirmation.getRoomCode()
        );
    }

    @Then("the confirmation message contains {string}")
    public void theConfirmationMessageContains(String expectedText) {
        assertNotNull(confirmation);

        assertTrue(
                confirmation.getMessage().contains(expectedText)
        );
    }

    @Then("the error message contains {string}")
    public void theErrorMessageContains(String expectedText) {
        assertNotNull(caughtException);

        assertTrue(
                caughtException.getMessage().contains(expectedText)
        );
    }

    @Then("a confirmation notification is sent")
    public void aConfirmationNotificationIsSent() {
        verify(notificationService)
                .sendConfirmation(any(ReservationConfirmation.class));
    }

    @Then("no confirmation notification is sent")
    public void noConfirmationNotificationIsSent() {
        verify(notificationService, never())
                .sendConfirmation(any(ReservationConfirmation.class));
    }
}