Feature: Accept reservation after an existing reservation

  Scenario: Requested period starts when existing reservation ends
    Given a room with code "R006", name "Paris", and maximum capacity 10
    And an existing reservation for room "R006" from "2026-06-10T10:00" to "2026-06-10T12:00"
    When the user "user@company.com" reserves room "R006" for 5 participants from "2026-06-10T12:00" to "2026-06-10T13:00"
    Then the reservation is accepted
    And the confirmation contains room code "R006"