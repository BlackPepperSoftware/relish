package com.example.steps;

import com.example.components.AddTaskPage;
import com.example.components.EditTaskPage;
import com.example.components.TaskPage;

import uk.co.blackpepper.relish.core.TableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cucumber.api.PendingException;
import cucumber.api.Transpose;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SomeSteps
{
    private TaskPage taskPage = new TaskPage();
    private AddTaskPage addTaskPage = new AddTaskPage();
    private EditTaskPage editTaskPage = new EditTaskPage();

    @Given("^I am on the task list$")
    public void iAmOnTheTaskList()
    {
        taskPage.launch();
    }

    @Then("^the list of tasks will be empty$")
    public void theListOfTasksWillBeEmpty()
    {
        taskPage.taskTable().assertEmpty();
    }

    @When("^I choose to add these tasks$")
    public void iChooseToAddThisTask(List<TableRow> tasks)
    {
        for(TableRow task : tasks)
        {
            taskPage.addButton().click();
            addTaskPage.set(task);
            addTaskPage.saveButton().click();
        }
    }

    @Then("^I will see this on the list of tasks$")
    public void iWillSeeThisOnTheListOfTasks(List<TableRow> tasks)
    {
        taskPage.taskTable().matches(tasks);
    }

    @When("^I will select these tasks$")
    public void iWillSelectTheseTasks(List<TableRow> tasks)
    {
        taskPage.taskTable().matches(tasks.stream().map(t -> t.except("select")).collect(Collectors.toList()));
        taskPage.taskTable().set(tasks.stream().map(t -> t.except("name")).collect(Collectors.toList()));
    }

    @When("^I choose to delete the selected tasks$")
    public void iChooseToDeleteTheSelectedTasks()
    {
        taskPage.deleteButton().click();
    }

    @Then("^the delete button is disabled$")
    public void theDeleteButtonIsDisabled()
    {
        taskPage.deleteButton().assertDisabled();
    }

    @And("^I change the '([^']*)' task to$")
    public void iChangeTheTaskTo(String name, @Transpose List<TableRow> task)
    {
        taskPage.taskTable().findFirst("name", name).getWidget(2).click();
        editTaskPage.set(task.get(0));
        editTaskPage.saveButton().click();
    }
}
