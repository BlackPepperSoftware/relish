package uk.co.blackpepper.relish.selenide;

import org.openqa.selenium.Keys;
import uk.co.blackpepper.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Input text.
 */
public class InputText extends InputWidget {
    /**
     * Instantiates a new Input text.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public InputText(By selector, Component parent) {
        super(selector, parent);
    }

    /**
     * Instantiates a new Input text.
     *
     * @param element the element
     * @param parent  the parent
     */
    public InputText(SelenideElement element, Component parent) {
        super(element, parent);
    }

    /**
     * Enter text.
     *
     * @param text the text
     */
    public void enterText(String text) {
        attempt(() -> {
            get().click();
            clear();
            if ((text != null) && (text.length() > 0)) {
                get().sendKeys(text);
            }
        }, 500, 2);
    }

    @Override
    public void setStringValue(String value) {
        enterText(value);
    }

    /**
     * Clear.
     */
    public void clear() {
        actions().click(get())
                .sendKeys(Keys.END)
                .keyDown(Keys.SHIFT)
                .sendKeys(Keys.HOME)
                .keyUp(Keys.SHIFT)
                .sendKeys(Keys.BACK_SPACE)
                .perform();
    }
}
