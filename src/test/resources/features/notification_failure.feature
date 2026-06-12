Feature: Do not send notification when reservation fails

  Scenario: No notification is sent after rejected reservation
    Given a room with code "R008", name "Rome", and maximum capacity 4
    And no existing reservation exists for room "R008"
    When the user "user@company.com" reserves room "R008" for 9 participants from "2026-06-10T14:00" to "2026-06-10T15:00"
    Then the reservation is rejected
    And no confirmation notification is sent