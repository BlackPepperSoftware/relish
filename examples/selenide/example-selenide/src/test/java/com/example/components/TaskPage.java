package com.example.components;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.selenide.Checkbox;
import uk.co.blackpepper.relish.selenide.Page;
import uk.co.blackpepper.relish.selenide.SelenideWidget;
import uk.co.blackpepper.relish.selenide.Table;

public class TaskPage extends Page
{
    public TaskPage()
    {
        super("/index.html");
    }

    public Table taskTable()
    {
        return new Table(By.className("tasks"), this)
            .withCellComponent("select", (tdCell) -> new Checkbox(tdCell.$("input"), this))
            .withCellComponent("4", (tdCell) -> new SelenideWidget(tdCell.$("button"), this))
            ;
    }

    public SelenideWidget addButton()
    {
        return new SelenideWidget(By.className("addButton"), this);
    }

    public SelenideWidget deleteButton()
    {
        return new SelenideWidget(By.className("deleteButton"), this);
    }
}
