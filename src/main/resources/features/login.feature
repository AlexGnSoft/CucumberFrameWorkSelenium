@SmokeFeature
Feature: Send emails

  @smoke
  Scenario Outline: Check sending emails and data collection

    Given User is on mail.ru landing page
    Then User clicks on enter to start login button
    When User enters <email> and <password>
    Then User is navigated to the mailbox home page
    And User sends email more then one time
    Then Verify messages counters
    Then Save data from each email
    And Send collected data to yourself
    Then Delete all received mails except the last one

    Examples:
      | email                | password         |
      | testoleksandrgnuskin | IhCKJTJV(@YVsh#Z |



