package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.ListWidget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Selenide.$;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public class Table extends ListWidget<SelenideElement>
{
    private Map<String,Function<SelenideElement,SelenideWidget>> builders = new HashMap<>();

    public Table(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    public Table(SelenideElement element, Component parent)
    {
        super(new SelenideWidget(element, parent), parent);
    }

    public Table withCellComponent(String heading, Function<SelenideElement,SelenideWidget> factory) {
        Table clone = new Table(get(), getParent());
        HashMap<String, Function<SelenideElement, SelenideWidget>> newBuilders = new HashMap<>(builders);
        newBuilders.put(heading, factory);
        clone.builders = newBuilders;
        return clone;
    }

    public Table withBuilders(Map<String,Function<SelenideElement,SelenideWidget>> builders) {
        Table clone = new Table(get(), getParent());
        clone.builders = builders;
        return clone;
    }

    @Override
    public HtmlRow get(int i)
    {
        List<WebElement> rows = items();

        if(rows.size() < i + 1)
        {
            throw new IllegalStateException("Not enough rows to read row " + i);
        }
        return createItem(rows.get(i)).withBuilders(builders);
    }

    @Override
    public int length()
    {
        return items().size();
    }

    public HtmlRow findFirst(Predicate<HtmlRow> htmlRowPredicate)
    {
        attempt(() -> findFirstImpl(htmlRowPredicate), 500, 4);
        return findFirstImpl(htmlRowPredicate);
    }

    private HtmlRow findFirstImpl(Predicate<HtmlRow> htmlRowPredicate)
    {

        return items()
            .stream()
            .map(e -> createItem(e))
            .filter(htmlRowPredicate)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No cell found for predicate"));
    }

    protected HtmlRow createItem(WebElement e)
    {
        return new HtmlRow($(e), this, Arrays.asList(headings()));
    }

    private String[] headings()
    {
        List<WebElement> th = get().findElements(By.tagName("th"));
        String[] ths = IntStream.range(0, th.size())
            .mapToObj(i -> toGetter(th.get(i).getText().length() > 0 ? th.get(i).getText() : "" + i))
            .collect(toList()).toArray(new String[]{});
        return ths;
    }

    private String toGetter(String s)
    {
        String join = String.join("", Arrays.stream(s.replaceAll("/", " ").split("\\s"))
            .map(s1 -> s1.substring(0, 1).toUpperCase() + s1.substring(1)).collect(toList()));
        return join.substring(0, 1).toLowerCase() + join.substring(1);
    }

    protected List<WebElement> items()
    {
        return get().findElements(By.tagName("tr")).stream()
            // Ignore rows without TD elements
            .filter(e -> !e.findElements(By.tagName("td")).isEmpty())
            .collect(toList());
    }
}
