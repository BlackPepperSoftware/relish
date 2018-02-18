package com.aspenshore.relish.selenide;

import com.aspenshore.relish.core.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aspenshore.relish.core.TestUtils.attempt;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Table extends SelenideWidget {
    public Table(By selector, Component parent) {
        super(selector, parent);
    }

    public Table(SelenideElement element, Component parent) {
        super(element, parent);
    }

    public String[] headings() {
        String[] ths = get().findElements(By.tagName("th")).stream()
                .map(e -> toGetter(e.getText())).collect(toList()).toArray(new String[]{});
        return ths;
    }

    /**
     * Get the data from the table as a list of getables
     *
     * This should be useful when checking the table against the contents of a table in a feature.
     *
     * @return list of getable objects
     */
    public List<Getable> data() {
        assertVisible();
        String[] headings = headings();
        List<List<String>> rowsWithTdStrings = get().findElements(By.tagName("tr")).stream()
                // Ignore rows without TD elements
                .filter(e -> !e.findElements(By.tagName("td")).isEmpty())
                // Then turn each row of TDs into a list of their string-contents
                .map(row -> row.findElements(By.tagName("td")).stream().map(e -> e.getText()).collect(toList()))
                .collect(toList());

        List<Getable> result = new ArrayList<>();
        for (List<String> rowWithTdStrings : rowsWithTdStrings) {
            GetableMap aResult = new GetableMap();
            assertEquals("Different number of headings and columns",
                    headings.length, rowWithTdStrings.size());
            for (int i = 0; i < headings.length; i++) {
                aResult.put(headings[i], rowWithTdStrings.get(i));
            }
            result.add(aResult);
        }
        return result;
    }

    public void matches(List<TableRow> sheetTable) {
        attempt(() -> {
            List<Getable> screenRows = data();
            int i = 0;
            for (TableRow assertRow : sheetTable) {
                assertThat(screenRows.get(i++), TableRowMatchers.getableMatchesAll(assertRow));
            }
        }, 500, 3);
    }

    private String toGetter(String s) {
        String join = String.join("", Arrays.stream(s.replaceAll("/", " ").split("\\s"))
                .map(s1 -> s1.substring(0, 1).toUpperCase() + s1.substring(1)).collect(toList()));
        return join.substring(0, 1).toLowerCase() + join.substring(1);
    }
}
