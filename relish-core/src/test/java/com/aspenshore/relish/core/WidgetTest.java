package com.aspenshore.relish.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class WidgetTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void aWidgetMustHaveAParent() {
        expectedException.expect(IllegalArgumentException.class);

        create("A peer", null);
    }

    @Test
    public void parentMustBeVisible() {
        Component parent = mock(Component.class);
        doThrow(new AssertionError("Not visible")).when(parent).assertVisible();
        expectedException.expect(AssertionError.class);
        create("A peer", parent);
    }

    @Test
    public void canGetTheSamePeerBack() {
        Component parent = mock(Component.class);
        Widget widget = create("A peer", parent);
        assertEquals(widget.get(), "A peer");
    }

    private Widget create(final String peer, final Component parent) {
        return new Widget<String>(peer, parent) {

            @Override
            public void assertVisible() {

            }

            @Override
            public String getStringValue() {
                return null;
            }

            @Override
            public void click() {
            }

            @Override
            public void assertInvisible() {

            }

            @Override
            public void assertDisabled() {

            }

            @Override
            public void assertEnabled() {

            }

            @Override
            public Widget<String> scrollTo() {
                return null;
            }
        };
    }
}