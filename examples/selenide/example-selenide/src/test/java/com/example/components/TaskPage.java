package com.example.components;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.selenide.HtmlTable;
import uk.co.blackpepper.relish.selenide.Page;
import uk.co.blackpepper.relish.selenide.SelenideWidget;

public class TaskPage extends Page {
    public TaskPage() {
        super("/index.html");
    }

    public HtmlTable taskTable() {
        return new HtmlTable(By.className("tasks"), this);
    }

    public SelenideWidget addButton() {
        return new SelenideWidget(By.className("addButton"), this);
    }
}
