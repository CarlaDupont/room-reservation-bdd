Feature: Reject reservation with invalid period

  Scenario: End date is before or equal to start date
    Given a room with code "R004", name "Tokyo", and maximum capacity 12
    And no existing reservation exists for room "R004"
    When the user "user@company.com" reserves room "R004" for 5 participants from "2026-06-10T11:00" to "2026-06-10T11:00"
    Then the reservation is rejected
    And the error message contains "Invalid reservation period"