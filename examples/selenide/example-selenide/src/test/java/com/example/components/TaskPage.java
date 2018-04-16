package com.example.components;

import uk.co.blackpepper.relish.selenide.Page;
import uk.co.blackpepper.relish.selenide.SelenideWidget;
import uk.co.blackpepper.relish.selenide.Table;
import org.openqa.selenium.By;

public class TaskPage extends Page {
    public TaskPage() {
        super("/index.html");
    }

    public Table taskTable() {
        return new Table(By.className("tasks"), this);
    }

    public SelenideWidget addButton() {
        return new SelenideWidget(By.className("addButton"), this);
    }
}
