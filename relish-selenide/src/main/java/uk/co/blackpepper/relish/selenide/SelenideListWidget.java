package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.ListWidget;
import uk.co.blackpepper.relish.core.Widget;

import java.util.function.Predicate;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public abstract class SelenideListWidget<T extends Widget> extends ListWidget<SelenideElement,T>
{
    public SelenideListWidget(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    public SelenideListWidget(SelenideElement element, Component parent)
    {
        super(new SelenideWidget(element, parent), parent);
    }

    public T findFirst(Predicate<T> itemPredicate)
    {
        attempt(() -> findFirstImpl(itemPredicate), 500, 4);
        return findFirstImpl(itemPredicate);
    }

    private T findFirstImpl(Predicate<T> itemPredicate)
    {

        return items()
            .stream()
            .map(this::createItem)
            .filter(itemPredicate)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No cell found for predicate"));
    }
}
