package com.aspenshore.relish.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;
import android.widget.TextView;
import com.aspenshore.relish.core.Component;
import junit.framework.AssertionFailedError;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static com.aspenshore.relish.core.TestUtils.attempt;
import static org.hamcrest.Matchers.not;

public class Checkbox extends EspressoWidget {
    public static final List<String> FALSISH = Arrays.asList("NO", "F", "N", "OFF", "0", "DISABLED", "0.0", "FALSE");

    public Checkbox(ViewInteraction peer, Component parent) {
        super(peer, parent);
    }

    public void check() {
        if (!isSelected()) {
            click();
        }
    }

    public void uncheck() {
        if (isSelected()) {
            click();
        }
    }

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
