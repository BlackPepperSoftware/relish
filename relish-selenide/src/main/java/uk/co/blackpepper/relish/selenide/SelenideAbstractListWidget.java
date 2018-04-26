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

/**
 * The type Selenide abstract list widget.
 *
 * @param <T> the type parameter
 */
public abstract class SelenideAbstractListWidget<T extends SelenideWidget> extends AbstractListWidget<SelenideElement, T>
{
    /**
     * Instantiates a new Selenide abstract list widget.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public SelenideAbstractListWidget(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    /**
     * Instantiates a new Selenide abstract list widget.
     *
     * @param element the element
     * @param parent  the parent
     */
    public SelenideAbstractListWidget(SelenideElement element, Component parent)
    {
        super(new SelenideWidget(element, parent), parent);
    }

    /**
     * Find first t.
     *
     * @param itemPredicate the item predicate
     * @return the t
     */
    public T findFirst(Predicate<T> itemPredicate)
    {
        attempt(() -> findFirstImpl(itemPredicate), 500, 8);
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

    /**
     * Items selector by.
     *
     * @return the by
     */
    public abstract By itemsSelector();

    @Override
    protected List<SelenideElement> items()
    {
        return new ArrayList<>(get().$$(itemsSelector()));
    }

    /**
     * Assert child count.
     *
     * @param expectedCount the expected count
     * @param predicate     the predicate
     */
    public void assertChildCount(int expectedCount, Predicate<T> predicate)
    {
        attempt(() -> {
            assertEquals(expectedCount, items().stream().map(this::createItem).filter(predicate).count());
        }, 1000, 10);
    }

    /**
     * All should be.
     *
     * @param condition the condition
     */
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
