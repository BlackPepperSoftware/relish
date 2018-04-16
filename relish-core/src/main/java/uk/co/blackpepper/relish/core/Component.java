package uk.co.blackpepper.relish.core;

import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static uk.co.blackpepper.relish.core.TableRowMatchers.getableMatchesAll;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static java.lang.String.format;
import static org.junit.Assert.assertThat;

/**
 * The type Component.
 */
public abstract class Component implements Getable {
    private Component parent;

    /**
     * Instantiates a new Component.
     *
     * @param parent the component containing this
     */
    public Component(Component parent) {
        this.parent = parent;
    }

    /**
     * Gets parent -- the component that contains this one.
     *
     * The parent may be null for containers, like pages and screens.
     *
     * @return the component containing this
     */
    public Component getParent() {
        return parent;
    }

    /**
     * Assert that the component is visible.
     */
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

    /**
     * Set the component to the values in the table-row.
     *
     * @param tableRow the table row
     * @return the component
     */
    public Component set(TableRow tableRow) {
        for (Map.Entry<String, String> entry : tableRow.entrySet()) {
            Object result = evaluateMethod(entry.getKey());
            if (result instanceof Component) {
                ((Component) result).setStringValue(entry.getValue());
            } else {
                throw new IllegalStateException("Cannot set the value for " + entry.getKey() + "()");
            }
        }
        return this;
    }

    /**
     * Check if the component matches the given table-row.
     *
     * @param tableRow the table row
     */
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
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(format("Unable to access %s() for %s", methodName, this), e);
        } catch (NoSuchMethodException nsme) {
            throw new IllegalStateException(format("Unable to find %s() for %s", methodName, this), nsme);
        } catch (InvocationTargetException ite) {
            throw new IllegalStateException(format("Unable to execute %s() for %s", methodName, this), ite);
        }
    }

    /**
     * Assert has value.
     *
     * @param s the s
     */
    public void assertHasValue(final String s) {
        attempt(new Runnable() {
            @Override
            public void run() {
                Assert.assertEquals(s, getStringValue());
            }
        }, 1000, 3);
    }

    /**
     * Gets string value.
     *
     * @return the string value
     */
    public abstract String getStringValue();

    /**
     * Sets string value.
     *
     * @param value the value
     */
    public void setStringValue(String value) {
        throw new IllegalStateException("Cannot set string value for " + this);
    }
}
