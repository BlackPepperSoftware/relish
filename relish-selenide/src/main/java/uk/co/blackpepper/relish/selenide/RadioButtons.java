package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import uk.co.blackpepper.relish.core.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.getSelectedRadio;
import static com.codeborne.selenide.Selenide.selectRadio;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Radio buttons.
 */
public class RadioButtons extends InputWidget {

    private final By selector;

    /**
     * Instantiates a new Radio buttons.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public RadioButtons(By selector, Component parent) {
        super(selector, parent);
        this.selector = selector;
    }

    @Override
    public void setStringValue(String option)
    {
        attempt(() ->
        {
            if (radioExistsFor(option)) {
                get().selectRadio(option);
            } else {
                $(By.xpath(String.format("//label[contains(text(),'%s')]", option))).click();
            }
        }, 1000, 10);
    }

    @Override
    public String getStringValue() {
        return $$(selector).filter(Condition.selected).first().getValue();
    }

    private boolean radioExistsFor(String option)
    {
        ElementsCollection allRadios = $$(selector);
        boolean exists = false;
        for (SelenideElement radio : allRadios) {
            String value = radio.getAttribute("value");
            if (value.equals(option)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
