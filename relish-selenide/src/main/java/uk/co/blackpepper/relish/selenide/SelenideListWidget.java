package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.ListWidget;
import uk.co.blackpepper.relish.core.Widget;

import java.util.List;
import java.util.function.Predicate;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public abstract class SelenideListWidget<T extends Widget> extends ListWidget<SelenideElement>
{
    public SelenideListWidget(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    public SelenideListWidget(SelenideElement element, Component parent)
    {
        super(new SelenideWidget(element, parent), parent);
    }

    @Override
    protected T get(int i)
    {
        List<WebElement> rows = items();

        if(rows.size() < i + 1)
        {
            throw new IllegalStateException("Not enough rows to read row " + i);
        }
        return createItem(rows.get(i));
    }


    @Override
    protected int length()
    {
        return items().size();
    }

    public T findFirst(Predicate<T> itemPredicate)
    {
        attempt(() -> findFirstImpl(itemPredicate), 500, 4);
        return findFirstImpl(itemPredicate);
    }

    protected abstract T createItem(WebElement e);

    protected abstract List<WebElement> items();

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
