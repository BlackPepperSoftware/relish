package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.core.AbstractListWidget;
import uk.co.blackpepper.relish.core.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public abstract class SelenideAbstractListWidget<T extends SelenideWidget> extends AbstractListWidget<SelenideElement, T>
{
    public SelenideAbstractListWidget(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    public SelenideAbstractListWidget(SelenideElement element, Component parent)
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

    public abstract By itemsSelector();

    @Override
    protected List<SelenideElement> items()
    {
        return new ArrayList<>(get().$$(itemsSelector()));
    }

    public void assertChildCount(int expectedCount, Predicate<T> predicate)
    {
        attempt(() -> {
            assertEquals(expectedCount, items().stream().map(this::createItem).filter(predicate).count());
        }, 1000, 10);
    }

    public void allShouldBe(Condition condition)
    {
        attempt(() -> {
            for(SelenideElement element : items())
            {
                T item = createItem(element);
                item.shouldBe(condition);
            }
        }, 1000, 3);
    }
}
