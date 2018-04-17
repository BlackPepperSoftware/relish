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
      | Name           |
      | Buy some bread |
      | Buy some milk  |
    Then I will see this on the list of tasks
      | Name           |
      | Buy some bread |
      | Buy some milk  |

  Scenario: Can delete tasks
    Given I am on the task list
    Then the delete button is disabled
    When I choose to add these tasks
      | Name           |
      | Buy some bread |
      | Buy some milk  |
      | Buy some water |
    And I will select these tasks
      | Name           | Select |
      | Buy some bread | true   |
      | Buy some milk  | false  |
      | Buy some water | true   |
    And I choose to delete the selected tasks
    Then I will see this on the list of tasks
      | Name          | Select |
      | Buy some milk | false  |

  Scenario: Can edit a task
    Given I am on the task list
    Then the delete button is disabled
    When I choose to add these tasks
      | Name           |
      | Buy some bread |
      | Buy some milk  |
      | Buy some water |
    And I change the 'Buy some milk' task to
      | Name | Buy some cream |
    Then I will see this on the list of tasks
      | Name           |
      | Buy some bread |
      | Buy some cream |
      | Buy some water |
