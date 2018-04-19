package com.example.components;

import uk.co.blackpepper.relish.selenide.Page;

public class AddTaskPage extends Page {
    public AddTaskPage() {
        super("/add.html");
    }

    public TaskForm form() {
        return new TaskForm(this);
    }
}
