package com.example.steps;

import com.aspenshore.relish.core.TableRow;
import com.example.components.AddTaskPage;
import com.example.components.TaskPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class SomeSteps {
    private TaskPage taskPage = new TaskPage();
    private AddTaskPage addTaskPage = new AddTaskPage();

    @Given("^I am on the task list$")
    public void iAmOnTheTaskList() {
        taskPage.launch();
    }

    @Then("^the list of tasks will be empty$")
    public void theListOfTasksWillBeEmpty() {
        taskPage.taskTable().matches(new ArrayList<>());
    }

    @When("^I choose to add this task$")
    public void iChooseToAddThisTask(List<TableRow> taskDetails) {
        taskPage.addButton().click();
        addTaskPage.set(taskDetails.get(0));
        addTaskPage.saveButton().click();
    }

    @Then("^I will see this on the list of tasks$")
    public void iWillSeeThisOnTheListOfTasks(List<TableRow> tasks) {
        taskPage.taskTable().matches(tasks);
    }
}
