package com.aspenshore.relish.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class WidgetTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void aWidgetMustHaveAParent() {
        expectedException.expect(IllegalArgumentException.class);

        new Widget<String>("A peer", null) {

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