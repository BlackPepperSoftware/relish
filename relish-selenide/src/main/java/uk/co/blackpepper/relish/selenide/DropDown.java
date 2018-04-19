package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import uk.co.blackpepper.relish.core.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.text;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public class DropDown extends InputWidget {
    public DropDown(By selector, Component parent) {
        super(selector, parent);
    }

    public DropDown(SelenideElement element, Component parent) {
        super(element, parent);
    }

    @Override
    public void setStringValue(String option)
    {
        assertVisible();
        attempt(() ->
        {
            if (options().contains(option)) {
                get().selectOption(option);
            } else {
                new Select(get().toWebElement()).selectByValue(option);
            }
        }, 1000, 10);
    }

    public void assertHasOptions(List<String> options)
    {
        assertVisible();
        attempt(() ->
        {
            List<String> actual = options();
            assertThat(actual, contains(options.toArray()));
        }, 1000, 10);
    }

    public void assertSelected(String option)
    {
        assertVisible();
        get().getSelectedOption().shouldHave(text(option));
    }

    private List<String> options()
    {
        ElementsCollection elements = get().$$(By.tagName("option"));
        return elements.stream().map(x -> x.text()).collect(Collectors.toList());
    }
}
