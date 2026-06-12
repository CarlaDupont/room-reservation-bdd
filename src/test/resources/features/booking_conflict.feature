Feature: Reject reservation when room is already booked

  Scenario: Requested period overlaps an existing reservation
    Given a room with code "R005", name "Oslo", and maximum capacity 10
    And an existing reservation for room "R005" from "2026-06-10T10:00" to "2026-06-10T12:00"
    When the user "user@company.com" reserves room "R005" for 5 participants from "2026-06-10T11:00" to "2026-06-10T13:00"
    Then the reservation is accepted
    And the error message contains "Room already booked"