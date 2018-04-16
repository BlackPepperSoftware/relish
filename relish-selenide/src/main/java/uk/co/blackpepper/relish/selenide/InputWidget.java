package uk.co.blackpepper.relish.selenide;

import uk.co.blackpepper.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public abstract class InputWidget extends SelenideWidget {
    public InputWidget(SelenideElement peer, Component parent) {
        super(peer, parent);
    }

    public InputWidget(By selector, Component parent) {
        super(selector, parent);
    }

    @Override
    public String getStringValue() {
        return get().getValue();
    }
}
