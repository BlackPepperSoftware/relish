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


/**
 * The type Page.
 */
public class Page extends Component
{
    private final String path;

    /**
     * Instantiates a new Page.
     *
     * @param path the path
     */
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

    /**
     * Matches url boolean.
     *
     * @param currentUrl the current url
     * @return the boolean
     */
    protected boolean matchesUrl(String currentUrl)
    {
        return currentUrl.endsWith(getPath());
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Refresh page.
     */
    public void refreshPage()
    {
        Selenide.refresh();
    }

    /**
     * Launch.
     */
    public void launch()
    {
        open(URI.create(Configuration.baseUrl).resolve(getPath()).toString());
    }
}
