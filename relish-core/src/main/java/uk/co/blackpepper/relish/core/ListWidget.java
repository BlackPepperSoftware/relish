package uk.co.blackpepper.relish.core;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static uk.co.blackpepper.relish.core.TableRowMatchers.getableMatchesAll;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Widget.
 *
 * @param <T> the type parameter
 */
public abstract class ListWidget<T> extends Widget<T> {
    private Widget<T> widget;

    /**
     * Instantiates a new Widget.
     *
     * @param widget   the widget for this list
     * @param parent the parent
     */
    public ListWidget(Widget<T> widget, Component parent) {
        super(widget.get(), parent);
        if (parent == null) {
            throw new IllegalArgumentException("Parent cannot be null");
        }
        parent.assertVisible();
        this.widget = widget;
    }

    protected abstract Widget<T> get(int i);

    protected abstract int length();

    public void matches(final List assertionValues) {
        attempt(new Runnable() {

            @Override
            public void run()
            {
                assertEquals(length(), assertionValues.size());
                for (int i = 0; i < assertionValues.size(); i++) {
                    Object assertionValue = assertionValues.get(i);
                    if (assertionValue instanceof TableRow) {
                        assertThat(get(i), getableMatchesAll((TableRow)assertionValue));
                    } else {
                        get(i).matches(assertionValue.toString());
                    }
                }
            }
        }, 500, 10);
    }

    public void assertEmpty() {
        attempt(new Runnable() {

            @Override
            public void run()
            {
                assertEquals(length(), 0);
            }
        }, 500, 10);
    }

    public void set(final List setValues) {
        attempt(new Runnable()
        {
            @Override
            public void run()
            {
                for (int i = 0; i < setValues.size(); i++) {
                    Object newValue = setValues.get(i);
                    if (newValue instanceof TableRow) {
                        get(i).set((TableRow)newValue);
                    } else {
                        get(i).setStringValue(newValue.toString());
                    }
                }
            }
        }, 500, 10);
    }

    @Override
    public String getStringValue()
    {
        throw new IllegalStateException("Cannot find a string value for an entire list widget");
    }

    @Override
    public T get() {
        return widget.get();
    }

    @Override
    public void click() {
        widget.click();
    }

    @Override
    public void assertInvisible() {
        widget.assertInvisible();
    }

    @Override
    public void assertVisible() {
        widget.assertVisible();
    }

    @Override
    public void assertDisabled() {
        widget.assertDisabled();
    }

    @Override
    public void assertEnabled() {
        widget.assertEnabled();
    }

    @Override
    public Widget<T> scrollTo() {
        return widget.scrollTo();
    }
}
