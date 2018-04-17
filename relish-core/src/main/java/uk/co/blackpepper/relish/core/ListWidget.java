package uk.co.blackpepper.relish.core;

import java.util.List;

import static org.junit.Assert.assertThat;
import static uk.co.blackpepper.relish.core.TableRowMatchers.getableMatchesAll;

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

    public abstract Widget<T> get(int i);

    public void matches(List assertionValues) {
        for (int i = 0; i < assertionValues.size(); i++) {
            Object assertionValue = assertionValues.get(i);
            if (assertionValue instanceof TableRow) {
                assertThat(get(i), getableMatchesAll((TableRow)assertionValue));
            } else {
                get(i).matches(assertionValue.toString());
            }
        }
    }

    @Override
    public String getStringValue()
    {
        throw new IllegalStateException("Cannot find a string value for an entire list widget");
    }

    /**
     * Get t.
     *
     * @return the t
     */
    public T get() {
        return widget.get();
    }

    /**
     * Click.
     */
    public void click() {
        widget.click();
    }

    /**
     * Assert invisible.
     */
    public void assertInvisible() {
        widget.assertInvisible();
    }

    public void assertVisible() {
        widget.assertVisible();
    }

    /**
     * Assert disabled.
     */
    public void assertDisabled() {
        widget.assertDisabled();
    }

    /**
     * Assert enabled.
     */
    public void assertEnabled() {
        widget.assertEnabled();
    }

    /**
     * Scroll to widget.
     *
     * @return the widget
     */
    public Widget<T> scrollTo() {
        return widget.scrollTo();
    }
}
