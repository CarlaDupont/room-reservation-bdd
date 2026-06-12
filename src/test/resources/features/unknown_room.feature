Feature: Reject reservation for unknown room

  Scenario: Room does not exist
    Given the room with code "UNKNOWN" does not exist
    When the user "user@company.com" reserves room "UNKNOWN" for 4 participants from "2026-06-10T10:00" to "2026-06-10T11:00"
    Then the reservation is accepted
    And the error message contains "Unknown room"