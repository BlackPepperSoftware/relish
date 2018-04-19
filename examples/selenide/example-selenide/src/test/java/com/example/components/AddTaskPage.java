package com.example.components;

import uk.co.blackpepper.relish.selenide.DropDown;
import uk.co.blackpepper.relish.selenide.InputText;
import uk.co.blackpepper.relish.selenide.Page;
import uk.co.blackpepper.relish.selenide.SelenideWidget;
import uk.co.blackpepper.relish.selenide.Table;
import org.openqa.selenium.By;

public class AddTaskPage extends Page {
    public AddTaskPage() {
        super("/add.html");
    }

    public InputText name() {
        return new InputText(By.id("name"), this);
    }

    public DropDown priority() {
        return new DropDown(By.id("priority"), this);
    }

    public SelenideWidget saveButton() {
        return new SelenideWidget(By.className("saveButton"), this);
    }
}
