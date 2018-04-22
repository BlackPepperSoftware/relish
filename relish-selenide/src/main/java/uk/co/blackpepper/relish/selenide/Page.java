package uk.co.blackpepper.relish.selenide;

import uk.co.blackpepper.relish.core.Component;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;


public class Page extends Component
{
    private final String path;

    public Page(String path)
    {
        super(null);
        this.path = path;
    }

    @Override
    public void assertVisible()
    {
        attempt(() -> {
            String currentUrl = getWebDriver().getCurrentUrl();
            try
            {
                assertTrue("Expected " + this + " to be visible",
                        this.matchesUrl(new URL(currentUrl).getPath())
                                || this.matchesUrl(new URL(currentUrl).toExternalForm()));
            }
            catch(MalformedURLException e)
            {
                throw new RuntimeException("Cannot parse the current URL: " + currentUrl);
            }
        }, 500, 20);
    }

    @Override
    public String getStringValue()
    {
        return null;
    }

    protected boolean matchesUrl(String currentUrl)
    {
        return currentUrl.endsWith(getPath());
    }

    public String getPath()
    {
        return path;
    }

    public void refreshPage()
    {
        Selenide.refresh();
    }

    public void launch()
    {
        open(URI.create(Configuration.baseUrl).resolve(getPath()).toString());
    }
}
