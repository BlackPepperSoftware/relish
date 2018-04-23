package uk.co.blackpepper.relish.selenide;

import uk.co.blackpepper.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

/**
 * The type Input widget.
 */
public abstract class InputWidget extends SelenideWidget {
    /**
     * Instantiates a new Input widget.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public InputWidget(SelenideElement peer, Component parent) {
        super(peer, parent);
    }

    /**
     * Instantiates a new Input widget.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public InputWidget(By selector, Component parent) {
        super(selector, parent);
    }

    @Override
    public String getStringValue() {
        return get().getValue();
    }
}
