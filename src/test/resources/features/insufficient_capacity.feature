Feature: Reject reservation when capacity is insufficient

  Scenario: Number of participants exceeds room capacity
    Given a room with code "R003", name "Madrid", and maximum capacity 6
    And no existing reservation exists for room "R003"
    When the user "user@company.com" reserves room "R003" for 9 participants from "2026-06-10T10:00" to "2026-06-10T11:00"
    Then the reservation is accepted
    And the error message contains "Capacity exceeded"