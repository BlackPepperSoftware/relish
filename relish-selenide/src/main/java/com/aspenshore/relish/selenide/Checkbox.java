package com.aspenshore.relish.selenide;

import com.aspenshore.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.aspenshore.relish.core.TestUtils.attempt;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.not;

public class Checkbox extends SelenideWidget {
    public static final List<String> FALSISH = Arrays.asList("NO", "F", "N", "OFF", "0", "DISABLED", "0.0", "FALSE");

    public Checkbox(By selector, Component parent) {
        super(selector, parent);
    }

    public Checkbox(SelenideElement elem, Component parent) {
        super(elem, parent);
    }

    public void uncheck() {
        assertVisible();
        if (get().isSelected()) {
            click();
        }
        get().shouldHave(not(checked));
    }

    public void check() {
        assertVisible();
        if (!get().isSelected()) {
            click();
        }
        get().shouldHave(checked);
    }

    public void assertChecked(final boolean isChecked) {
        assertVisible();
        attempt(() ->
        {
            if(isChecked)
            {
                get().shouldHave(checked);
            } else {
                get().shouldNotHave(checked);
            }
        }, 1000, 3);
    }

    public String getStringValue() {
        assertVisible();
        return get().isSelected() ? "true" : "false";
    }

    public void setStringValue(String value) {
        assertVisible();
        if (FALSISH.contains(value.toUpperCase(Locale.ENGLISH))) {
            uncheck();
        } else {
            check();
        }
    }
}
