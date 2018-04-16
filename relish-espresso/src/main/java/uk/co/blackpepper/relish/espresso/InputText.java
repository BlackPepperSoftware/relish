package uk.co.blackpepper.relish.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.widget.TextView;
import uk.co.blackpepper.relish.core.Component;
import org.hamcrest.Matcher;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

public class InputText extends EspressoWidget {
    public InputText(ViewInteraction peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public String getStringValue() {
        return text();
    }

    @Override
    public void setStringValue(String s) {
        enterText(s);
    }

    public void enterText(String text) {
        click();
        clearText();
        get().perform(ViewActions.typeText(text));
    }

    private String text() {
        final String[] stringHolder = { null };
        get().perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    private void clearText() {
        get().perform(ViewActions.clearText()).perform(closeSoftKeyboard());
    }
}
