package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.SelenideElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.ListWidget;
import uk.co.blackpepper.relish.core.TestUtils;
import uk.co.blackpepper.relish.core.Widget;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static java.util.stream.Collectors.toList;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public class HtmlTable extends ListWidget<SelenideElement>
{
    public HtmlTable(By selector, Component parent)
    {
        super(new SelenideWidget(selector, parent), parent);
    }

    public HtmlTable(SelenideElement element, Component parent)
    {
        super(new SelenideWidget(element, parent), parent);
    }

    @Override
    public Widget<SelenideElement> get(int i)
    {
        attempt(() -> {
            List<WebElement> rows = getRows();

            if(rows.size() < i + 1)
            {
                throw new IllegalStateException("Not enough rows to read row " + i);
            }
        }, 500, 20);
        return new HtmlRow($(getRows().get(i)), this, Arrays.asList(headings()));
    }

    private List<WebElement> getRows()
    {
        return get().findElements(By.tagName("tr")).stream()
                // Ignore rows without TD elements
                .filter(e -> !e.findElements(By.tagName("td")).isEmpty())
                .collect(toList());
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
}
