Feature: Reservation at maximum capacity

  Scenario: Reservation accepted at maximum capacity
    Given a room with code "R002", name "Berlin", and maximum capacity 8
    And no existing reservation exists for room "R002"
    When the user "user@company.com" reserves room "R002" for 8 participants from "2026-06-10T09:00" to "2026-06-10T10:00"
    Then the reservation is rejected
    And the confirmation contains room code "R002"