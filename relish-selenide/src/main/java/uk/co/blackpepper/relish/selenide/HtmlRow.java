package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.TableRow;
import uk.co.blackpepper.relish.core.Widget;
import uk.co.blackpepper.relish.core.WidgetContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.codeborne.selenide.Selenide.$;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Html row.
 */
public class HtmlRow extends SelenideWidget implements WidgetContainer
{
    private final List<String> headings;
    private Map<String,Function<SelenideElement,SelenideWidget>> builders = new HashMap<>();

    /**
     * Instantiates a new Html row.
     *
     * @param selector the selector
     * @param parent   the parent
     * @param headings the headings
     */
    public HtmlRow(By selector, Component parent, List<String> headings) {
        super(selector, parent);
        this.headings = headings;
    }

    /**
     * Instantiates a new Html row.
     *
     * @param element  the element
     * @param parent   the parent
     * @param headings the headings
     */
    public HtmlRow(SelenideElement element, Component parent, List<String> headings) {
        super(element, parent);
        this.headings = headings;
    }

    /**
     * With builders html row.
     *
     * @param builders the builders
     * @return the html row
     */
    public HtmlRow withBuilders(Map<String,Function<SelenideElement,SelenideWidget>> builders) {
        HtmlRow clone = new HtmlRow(get(), getParent(), headings);
        clone.builders = builders;
        return clone;
    }

    @Override
    public String get(String key) {
        return getWidget(key).getStringValue();
    }

    @Override
    public Widget getWidget(String key)
    {
        int headingPos = headings.indexOf(key);
        if (headingPos == -1) {
            throw new IllegalStateException("Cannot find heading '" + key + "'");
        }
        List<WebElement> cells = cells();
        if (cells.size() < headingPos + 1) {
            throw new IllegalStateException("Not enough cells for '" + key + "'. Needed "
                + headingPos + " but only had " + cells.size() + ". Cells = " + cells);
        }
        return createWidgetFor(key, $(cells.get(headingPos)));
    }

    /**
     * Gets widget.
     *
     * @param column the column
     * @return the widget
     */
    public Widget getWidget(int column) {
        String heading = (headings.size() > column) ? headings.get(column) : "" + column;
        return createWidgetFor(heading, $(cells().get(column)));
    }

    private SelenideWidget createWidgetFor(String heading, SelenideElement rowElement)
    {
        if (builders.containsKey(heading)) {
            return builders.get(heading).apply($(rowElement));
        }
        return new SelenideWidget(rowElement, this);
    }

    /**
     * Columns int.
     *
     * @return the int
     */
    public int columns() {
        return cells().size();
    }

    private List<WebElement> cells()
    {
        return get().findElements(By.tagName("td"));
    }
}
