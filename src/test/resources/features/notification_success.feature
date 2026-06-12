Feature: Send notification when reservation is accepted

  Scenario: Confirmation notification is sent after successful reservation
    Given a room with code "R007", name "London", and maximum capacity 10
    And no existing reservation exists for room "R007"
    When the user "user@company.com" reserves room "R007" for 3 participants from "2026-06-10T14:00" to "2026-06-10T15:00"
    Then the reservation is rejected
    And a confirmation notification is sent