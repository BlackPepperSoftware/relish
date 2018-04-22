package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.Widget;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementShould;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

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

    public void click(int x, int y) {
        SelenideElement element = get();
        actions().moveToElement(element, x, y).click().perform();
    }

    public String data(String name) {
        return get().getAttribute("data-" + name);
    }

    @Override
    public void assertInvisible() {
        attempt(() -> {
            try {
                get().shouldBe(not(visible));
            } catch (ElementShould e) {
                // Do not throw state element exception because it will mean that it is invisible
                if (!(e.getCause() instanceof StaleElementReferenceException)) {
                    throw e;
                }
            }
        }, 500, 2);
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

    public Actions actions() {
        return new Actions(driver());
    }

    public WebDriver driver() {
        return WebDriverRunner.getWebDriver();
    }
}
