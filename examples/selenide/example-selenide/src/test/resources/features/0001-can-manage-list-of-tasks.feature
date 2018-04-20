Feature: A list of tasks can be managed by the application
  As a user
  I want to be able to create, read, update and delete tasks
  So that I can manage my time

  Scenario: Initially the list of tasks is empty
    Given I am on the task list
    Then the list of tasks will be empty

  Scenario: I can add a task
    Given I am on the task list
    When I choose to add these tasks
      | Name           | Priority | Status  |
      | Buy some bread | H        | ready   |
      | Buy some milk  | L        | waiting |
    Then I will see this on the list of tasks
      | Name           | Priority | Status  |
      | Buy some bread | High     | Ready   |
      | Buy some milk  | Low      | Waiting |

  Scenario: Can delete tasks
    Given I am on the task list
    Then the delete button is disabled
    When I choose to add these tasks
      | Name           | Priority |
      | Buy some bread | H        |
      | Buy some milk  | M        |
      | Buy some water | L        |
    And I will select these tasks
      | Name           | Priority | Select |
      | Buy some bread | High     | true   |
      | Buy some milk  | Medium   | false  |
      | Buy some water | Low      | true   |
    And I choose to delete the selected tasks
    Then I will see this on the list of tasks
      | Name          | Priority | Select |
      | Buy some milk | Medium   | false  |

  Scenario: Can edit a task
    Given I am on the task list
    When I choose to add these tasks
      | Name           | Priority | Status  |
      | Buy some bread | Medium   | Ready   |
      | Buy some milk  | Low      | waiting |
      | Buy some water | High     | pending |
    And I edit the 'Buy some milk' task
    Then the edit form will contain
      | Name     | Buy some milk |
      | Priority | L             |
      | Status   | waiting       |
    When I save these changes
      | Name     | Buy some cream |
      | Priority | M              |
      | Status   | done           |
    Then I will see this on the list of tasks
      | Name           | Priority | Status  |
      | Buy some bread | Medium   | Ready   |
      | Buy some cream | Medium   | Done    |
      | Buy some water | High     | Pending |
