package com.example.components;

import com.aspenshore.relish.selenide.InputText;
import com.aspenshore.relish.selenide.Page;
import com.aspenshore.relish.selenide.SelenideWidget;
import com.aspenshore.relish.selenide.Table;
import org.openqa.selenium.By;

public class AddTaskPage extends Page {
    public AddTaskPage() {
        super("/add.html");
    }

    public InputText name() {
        return new InputText(By.id("name"), this);
    }

    public SelenideWidget saveButton() {
        return new SelenideWidget(By.className("saveButton"), this);
    }
}
