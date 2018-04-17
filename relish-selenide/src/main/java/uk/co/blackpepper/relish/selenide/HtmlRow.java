package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.TableRow;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public class HtmlRow extends SelenideWidget {
    private final List<String> headings;

    public HtmlRow(By selector, Component parent, List<String> headings) {
        super(selector, parent);
        this.headings = headings;
    }

    public HtmlRow(SelenideElement element, Component parent, List<String> headings) {
        super(element, parent);
        this.headings = headings;
    }

    @Override
    public String get(String key) {
        int headingPos = headings.indexOf(key);
        if (headingPos == -1) {
            throw new IllegalStateException("Cannot find heading '" + key + "'");
        }
        List<WebElement> cells = cells();
        if (cells.size() < headingPos + 1) {
            throw new IllegalStateException("Not enough cells for '" + key + "'. Needed "
                + headingPos + " but only had " + cells.size() + ". Cells = " + cells);
        }
        SelenideElement rowElement = $(cells.get(headingPos));
        return new SelenideWidget(rowElement, this).getStringValue();
    }

    public int columns() {
        return cells().size();
    }

    private List<WebElement> cells()
    {
        return get().findElements(By.tagName("td"));
    }
}
