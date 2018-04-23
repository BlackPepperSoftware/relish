package uk.co.blackpepper.relish.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;
import android.widget.TextView;
import uk.co.blackpepper.relish.core.Component;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static org.hamcrest.Matchers.not;

/**
 * The type Checkbox.
 */
public class Checkbox extends EspressoWidget {
    private static final List<String> FALSISH = Arrays.asList("NO", "F", "N", "OFF", "0", "DISABLED", "0.0", "FALSE");

    /**
     * Instantiates a new Checkbox.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public Checkbox(ViewInteraction peer, Component parent) {
        super(peer, parent);
    }

    /**
     * Check.
     */
    public void check() {
        if (!isSelected()) {
            click();
        }
    }

    /**
     * Uncheck.
     */
    public void uncheck() {
        if (isSelected()) {
            click();
        }
    }

    /**
     * Assert checked.
     *
     * @param isChecked the is checked
     */
    public void assertChecked(final boolean isChecked) {
        assertVisible();
        attempt(new Runnable() {
            @Override
            public void run() {
                if (isChecked) {
                    Checkbox.this.get().check(ViewAssertions.matches(isChecked()));
                } else {
                    Checkbox.this.get().check(ViewAssertions.matches(not(isChecked())));
                }
            }
        }, 1000, 3);
    }

    public String getStringValue() {
        assertVisible();
        return isSelected() ? "true" : "false";
    }

    /**
     * Is selected boolean.
     *
     * @return the boolean
     */
    public boolean isSelected() {
        assertVisible();
        try {
            get().check(ViewAssertions.matches(isChecked()));
            return true;
        } catch(AssertionFailedError e) {
            return false;
        }
    }

    public void setStringValue(String value) {
        assertVisible();
        if (FALSISH.contains(value.toUpperCase(Locale.ENGLISH))) {
            uncheck();
        } else {
            check();
        }
    }
}
