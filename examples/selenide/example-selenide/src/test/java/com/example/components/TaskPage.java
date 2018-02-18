package com.example.components;

import com.aspenshore.relish.selenide.Page;
import com.aspenshore.relish.selenide.SelenideWidget;
import com.aspenshore.relish.selenide.Table;
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
