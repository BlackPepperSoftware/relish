package com.aspenshore.relish.selenide;

import com.aspenshore.relish.core.Component;
import com.aspenshore.relish.core.Widget;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class SelenideWidget extends Widget<SelenideElement> {
    public SelenideWidget(By selector, Component parent) {
        this($(selector), parent);
    }

    public SelenideWidget(SelenideElement peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public void click() {
        get().click();
    }

    @Override
    public void assertInvisible() {
        get().shouldBe(visible);
    }

    @Override
    public void assertDisabled() {
        try {
            get().shouldBe(not(enabled));
        } catch (ElementShould e) {
            // Do not throw state element exception because it will mean that it is invisible
            if (!(e.getCause() instanceof StaleElementReferenceException)) {
                throw e;
            }
        }
    }

    @Override
    public void assertEnabled() {
        get().shouldBe(enabled);
    }

    @Override
    public SelenideWidget scrollTo() {
        get().scrollTo();
        return this;
    }

    @Override
    public void assertVisible() {
        get().shouldBe(visible);
    }

    @Override
    public String getStringValue() {
        return get().getText().trim();
    }
}
