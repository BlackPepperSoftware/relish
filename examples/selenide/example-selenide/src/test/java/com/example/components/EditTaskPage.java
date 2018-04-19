package com.example.components;

import uk.co.blackpepper.relish.selenide.Page;

public class EditTaskPage extends Page {
    public EditTaskPage() {
        super("/edit.html");
    }

    public TaskForm form() {
        return new TaskForm(this);
    }
}
