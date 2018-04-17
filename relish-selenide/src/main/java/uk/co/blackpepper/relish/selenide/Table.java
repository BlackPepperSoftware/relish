package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.ListWidget;
import uk.co.blackpepper.relish.core.Widget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    public Table withBuilder(String heading, Function<SelenideElement,SelenideWidget> factory) {
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
    public Widget<SelenideElement> get(int i)
    {
        List<WebElement> rows = getRows();

        if(rows.size() < i + 1)
        {
            throw new IllegalStateException("Not enough rows to read row " + i);
        }
        return new HtmlRow($(rows.get(i)), this, Arrays.asList(headings())).withBuilders(builders);
    }

    @Override
    public int length()
    {
        return getRows().size();
    }

    public HtmlRow findFirst(String heading, String value)
    {
        attempt(() -> findFirstImpl(heading, value), 500, 4);
        return findFirstImpl(heading, value);
    }

    private HtmlRow findFirstImpl(String heading, String value)
    {
        List<String> headingList = Arrays.asList(headings());
        assertTrue("Cannot find heading '" + heading + "'", headingList.contains(heading));
        int len = length();
        for (int i = 0; i < len; i++) {
            HtmlRow htmlRow = (HtmlRow) get(i);
            if (value.equals(htmlRow.get(heading))) {
                return htmlRow;
            }
        }
        throw new AssumptionViolatedException("Cannot find a row with '" + heading + "' set to '" + value + "'");
    }

    private String[] headings()
    {
        String[] ths = get().findElements(By.tagName("th")).stream()
            .map(e -> toGetter(e.getText())).collect(toList()).toArray(new String[]{});
        return ths;
    }

    private String toGetter(String s)
    {
        String join = String.join("", Arrays.stream(s.replaceAll("/", " ").split("\\s"))
            .map(s1 -> s1.substring(0, 1).toUpperCase() + s1.substring(1)).collect(toList()));
        return join.substring(0, 1).toLowerCase() + join.substring(1);
    }

    private List<WebElement> getRows()
    {
        return get().findElements(By.tagName("tr")).stream()
            // Ignore rows without TD elements
            .filter(e -> !e.findElements(By.tagName("td")).isEmpty())
            .collect(toList());
    }
}
