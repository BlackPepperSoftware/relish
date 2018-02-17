package com.aspenshore.relish.selenide;

import com.aspenshore.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.aspenshore.relish.core.TestUtils.attempt;

public class InputText extends InputWidget {
    public InputText(By selector, Component parent) {
        super(selector, parent);
    }

    public InputText(SelenideElement element, Component parent) {
        super(element, parent);
    }

    public void enterText(String text) {
        attempt(() -> {
            get().click();
            get().clear();
            get().sendKeys(text);
        }, 500, 2);
    }

    @Override
    public void setStringValue(String value) {
        enterText(value);
    }
}
