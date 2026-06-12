Feature: Accepted room reservation

  Scenario: Reservation accepted
    Given a room with code "R001", name "Atlas", and maximum capacity 10
    And no existing reservation exists for room "R001"
    When the user "user@company.com" reserves room "R001" for 5 participants from "2026-06-10T10:00" to "2026-06-10T11:00"
    Then the reservation is accepted
    And the confirmation contains room code "R001"
    And the confirmation message contains "user@company.com"