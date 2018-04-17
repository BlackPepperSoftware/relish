Feature: A list of tasks can be managed by the application
  As a user
  I want to be able to create, read, update and delete tasks
  So that I can manage my time

  Scenario: Initially the list of tasks is empty
    Given I am on the task list
    Then the list of tasks will be empty

  Scenario: I can add a task
    Given I am on the task list
    When I choose to add this task
      | Name           |
      | Buy some bread |
    Then I will see this on the list of tasks
      | Name           |
      | Buy some bread |

  Scenario: Can delete tasks
    Given I am on the task list
    Then the delete button is disabled
    When I choose to add this task
      | Name           |
      | Buy some bread |
    And I choose to add this task
      | Name          |
      | Buy some milk |
    And I choose to add this task
      | Name           |
      | Buy some water |
    And I will select these tasks
      | Name           | Selected |
      | Buy some bread | true     |
      | Buy some milk  | false    |
      | Buy some water | true     |
    And I choose to delete the selected tasks
    Then I will see this on the list of tasks
      | Name          |
      | Buy some milk |
