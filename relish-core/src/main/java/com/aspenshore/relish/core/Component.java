package com.aspenshore.relish.core;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static com.aspenshore.relish.core.TableRowMatchers.getableMatchesAll;
import static com.aspenshore.relish.core.TestUtils.attempt;
import static org.junit.Assert.assertThat;

public abstract class Component implements Getable {
    private Component parent;

    public Component(Component parent) {
        this.parent = parent;
    }

    public Component getParent() {
        return parent;
    }

    public abstract void assertVisible();

    @Override
    public String get(String key) {
        Object result = evaluateMethod(key);
        if (result instanceof Component) {
            return ((Component) result).getStringValue();
        } else {
            return result.toString();
        }
    }

    public Component set(TableRow tableRow) {
        for (Map.Entry<String, String> entry : tableRow.entrySet()) {
            Object result = evaluateMethod(entry.getKey());
            if (result instanceof Component) {
                ((Component) result).setStringValue(entry.getValue());
            } else {
                throw new RuntimeException("Cannot set the value for " + entry.getKey() + "()");
            }
        }
        return this;
    }

    public void matches(final TableRow tableRow) {
        assertVisible();
        attempt(new Runnable() {
            @Override
        public void run() {
            assertThat(Component.this, getableMatchesAll(tableRow));
        }
        }, 2000, 3);
    }

    private Object evaluateMethod(String methodName) {
        Class<? extends Component> clazz = this.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName);
            return method.invoke(this);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Unable to execute " + methodName + "() for " + this, e);
        }
    }

    public void assertHasValue(final String s) {
        attempt(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(s, getStringValue());
            }
        }, 1000, 3);
    }

    public abstract String getStringValue();

    public void setStringValue(String value) {
        throw new RuntimeException("Cannot set string value for " + this);
    }
}
