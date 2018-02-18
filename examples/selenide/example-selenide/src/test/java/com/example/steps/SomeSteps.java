package com.example.steps;

import com.example.components.TaskPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.ArrayList;

public class SomeSteps {
    private TaskPage taskPage = new TaskPage();

    @Given("^I am on the task list$")
    public void iAmOnTheTaskList() {
        taskPage.launch();
    }

    @Then("^the list of tasks will be empty$")
    public void theListOfTasksWillBeEmpty() {
        taskPage.taskTable().matches(new ArrayList<>());
    }
}
