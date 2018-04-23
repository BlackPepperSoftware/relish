package uk.co.blackpepper.relish.selenide;

import uk.co.blackpepper.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.not;

/**
 * The type Checkbox.
 */
public class Checkbox extends SelenideWidget {
    private static final List<String> FALSISH = Arrays.asList("NO", "F", "N", "OFF", "0", "DISABLED", "0.0", "FALSE");

    /**
     * Instantiates a new Checkbox.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public Checkbox(By selector, Component parent) {
        super(selector, parent);
    }

    /**
     * Instantiates a new Checkbox.
     *
     * @param elem   the elem
     * @param parent the parent
     */
    public Checkbox(SelenideElement elem, Component parent) {
        super(elem, parent);
    }

    @Override
    public String getStringValue() {
        assertVisible();
        return get().isSelected() ? "true" : "false";
    }

    @Override
    public void setStringValue(String value) {
        assertVisible();
        if (FALSISH.contains(value.toUpperCase(Locale.ENGLISH))) {
            uncheck();
        } else {
            check();
        }
    }

    /**
     * Uncheck.
     */
    public void uncheck() {
        assertVisible();
        if (get().isSelected()) {
            click();
        }
        get().shouldHave(not(checked));
    }

    /**
     * Check.
     */
    public void check() {
        assertVisible();
        if (!get().isSelected()) {
            click();
        }
        get().shouldHave(checked);
    }

    /**
     * Assert checked.
     *
     * @param isChecked the is checked
     */
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
}
