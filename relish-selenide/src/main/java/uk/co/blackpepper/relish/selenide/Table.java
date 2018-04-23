package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The type Table.
 */
public class Table extends SelenideAbstractListWidget<HtmlRow>
{
    private Map<String,Function<SelenideElement,SelenideWidget>> builders = new HashMap<>();

    /**
     * Instantiates a new Table.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public Table(By selector, Component parent)
    {
        super(selector, parent);
    }

    /**
     * Instantiates a new Table.
     *
     * @param element the element
     * @param parent  the parent
     */
    public Table(SelenideElement element, Component parent)
    {
        super(element, parent);
    }

    @Override
    public By itemsSelector() {
        return By.xpath("//tr[td]");
    }

    /**
     * With cell component table.
     *
     * @param heading the heading
     * @param factory the factory
     * @return the table
     */
    public Table withCellComponent(String heading, Function<SelenideElement,SelenideWidget> factory) {
        Table clone = new Table(get(), getParent());
        HashMap<String, Function<SelenideElement, SelenideWidget>> newBuilders = new HashMap<>(builders);
        newBuilders.put(heading, factory);
        clone.builders = newBuilders;
        return clone;
    }

    @Override
    protected HtmlRow createItem(SelenideElement e)
    {
        List<String> headings = headings();
        return new HtmlRow($(e), this, headings).withBuilders(builders);
    }

    private List<String> headings()
    {
        List<WebElement> th = get().findElements(By.tagName("th"));
        return Arrays.asList(IntStream.range(0, th.size())
            .mapToObj(i -> toGetter(th.get(i).getText().length() > 0 ? th.get(i).getText() : "" + i))
            .collect(toList()).toArray(new String[]{}));
    }

    private String toGetter(String s)
    {
        String join = String.join("", Arrays.stream(s.replaceAll("/", " ").split("\\s"))
            .map(s1 -> s1.substring(0, 1).toUpperCase() + s1.substring(1)).collect(toList()));
        return join.substring(0, 1).toLowerCase() + join.substring(1);
    }
}
