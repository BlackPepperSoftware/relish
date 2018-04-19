package com.example.components;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.selenide.DropDown;
import uk.co.blackpepper.relish.selenide.InputText;
import uk.co.blackpepper.relish.selenide.RadioButtons;
import uk.co.blackpepper.relish.selenide.SelenideWidget;

public class TaskForm extends SelenideWidget {
    public TaskForm(Component parent) {
        super(By.cssSelector("body"), parent);
    }

    public InputText name() {
        return new InputText(By.id("name"), this);
    }

    public DropDown priority() {
        return new DropDown(By.id("priority"), this);
    }

    public RadioButtons status() {
        return new RadioButtons(By.name("status"), this);
    }

    public SelenideWidget saveButton() {
        return new SelenideWidget(By.className("saveButton"), this);
    }
}
