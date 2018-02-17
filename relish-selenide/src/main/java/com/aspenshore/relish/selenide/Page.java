package com.aspenshore.relish.selenide;

import com.aspenshore.relish.core.Component;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import java.net.URI;

import static com.aspenshore.relish.core.TestUtils.attempt;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;


public class Page extends Component {
    private final String path;

    public Page(String path) {
        super(null);
        this.path = path;
    }

    @Override
    public void assertVisible() {
        attempt(new Runnable() {
            @Override
            public void run() {
                String currentUrl = getWebDriver().getCurrentUrl();
                boolean condition = Page.this.matchesUrl(currentUrl);
                assertTrue(condition);
            }
        }, 500, 20);
    }

    @Override
    public String getStringValue() {
        return null;
    }

    protected boolean matchesUrl(String currentUrl) {
        return currentUrl.endsWith(getPath());
    }

    public String getPath() {
        return path;
    }

    public void refreshPage() {
        Selenide.refresh();
    }

    public void launch() {
        open(URI.create(Configuration.baseUrl).resolve(getPath()).toString());
    }
}
