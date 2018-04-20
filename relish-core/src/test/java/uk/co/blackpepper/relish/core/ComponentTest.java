package uk.co.blackpepper.relish.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComponentTest
{
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void canGetValuesFromStringMethods()
    {
        ExampleComponent component = new ExampleComponent(mock(Component.class))
        {
            public String a()
            {
                return "I am A";
            }

            public String b()
            {
                return "I am B";
            }
        };
        assertEquals("I am A", component.get("a"));
        assertEquals("I am B", component.get("b"));
    }

    @Test
    public void canSetValueOnSubComponents()
    {
        ExampleComponent component = new ExampleComponent(null);
        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("subComponent1", "Value 1!");
        tableRowMap.put("subComponent2", "Value 2!");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());
        component.set(tableRow);

        assertEquals(component.subComponent1.getStringValue(), "Value 1!");

        assertEquals(component.subComponent2.getStringValue(), "Value 2!");
    }

    @Test
    public void canSetValueOnSubComponentsInWidgetContainers()
    {
        Component parent = mock(Component.class);
        ExampleWidgetContainer component = new ExampleWidgetContainer(parent);
        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("Number1", "Value A!");
        tableRowMap.put("Number2", "Value B!");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());
        component.set(tableRow);

        assertEquals(component.innerWidget1.getStringValue(), "Value A!");

        assertEquals(component.innerWidget2.getStringValue(), "Value B!");
    }

    @Test
    public void canGetTheParentOutOfTheComponent()
    {
        Component parent = mock(Component.class);
        ExampleComponent exampleComponent = new ExampleComponent(parent);
        assertEquals(exampleComponent.getParent(), parent);
    }

    @Test
    public void canGetTheStringValueOfASubComponentByName()
    {
        ExampleComponent component = new ExampleComponent(mock(Component.class));
        component.subComponent2.setStringValue("Val2");
        assertEquals("Val2", component.get("subComponent2"));
    }

    @Test
    public void cannotSetTheValueOfASubThingIfItIsNotAWidget()
    {
        ExampleComponentWithAComponentLikeMethodThatDoesNotReturnAComponent component
            = new ExampleComponentWithAComponentLikeMethodThatDoesNotReturnAComponent(mock(Component.class));

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("notASubComponent", "A new value!");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Cannot set the value for notASubComponent()");

        component.set(tableRow);
    }

    @Test
    public void canMatchValuesOnSubComponents()
    {
        ExampleComponent component = new ExampleComponent(null);

        component.subComponent1.setStringValue("actual value 1");
        component.subComponent2.setStringValue("actual value 2");

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("subComponent1", "actual value 1");
        tableRowMap.put("subComponent2", "actual value 2");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        component.matches(tableRow);
    }

    @Test
    public void ifMatchAgainstTableRowFailsItWillThrowTheCorrectException()
    {
        ExampleComponent component = new ExampleComponent(null);

        component.subComponent1.setStringValue("actual value 1");
        component.subComponent2.setStringValue("actual value 2");

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("subComponent1", "actual value 1");
        tableRowMap.put("subComponent2", "not the actual value 2");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Failed after several retries:");

        component.matches(tableRow);
    }

    @Test
    public void failIfTryToAccessPrivateSubComponent() {
        ExampleComponent component = new ExampleComponent(null);

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("privateComponent", "actual value 1");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        try {
            component.matches(tableRow);
        } catch(IllegalStateException ise) {
            Throwable cause = ise.getCause();
            assertTrue(cause instanceof IllegalStateException);
            assertTrue(cause.getMessage().startsWith("Unable to access privateComponent()"));
        }
    }

    @Test
    public void failIfTryToAccessNonExistentSubComponent() {
        ExampleComponent component = new ExampleComponent(null);

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("iDoNotExist", "actual value 1");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        try {
            component.matches(tableRow);
        } catch(IllegalStateException ise) {
            Throwable cause = ise.getCause();
            assertTrue(cause instanceof IllegalStateException);
            assertTrue(cause.getMessage().startsWith("Unable to find iDoNotExist()"));
        }
    }

    @Test
    public void failIfAccessingSubComponentThrowsException() {
        ExampleComponent component = new ExampleComponent(null);

        TableRow tableRow = mock(TableRow.class);
        Map<String, String> tableRowMap = new HashMap<>();
        tableRowMap.put("erroringMethod", "actual value 1");
        when(tableRow.entrySet()).thenReturn(tableRowMap.entrySet());

        try {
            component.matches(tableRow);
        } catch(IllegalStateException ise) {
            Throwable cause = ise.getCause();
            assertTrue(cause instanceof IllegalStateException);
            assertTrue(cause.getMessage().startsWith("Error executing erroringMethod()"));
        }
    }
}

class ExampleInputComponent extends Widget
{
    public String value;

    public ExampleInputComponent(Component parent)
    {
        super(null, parent);
    }

    @Override
    public void click()
    {
    }

    @Override
    public void assertInvisible()
    {
    }

    @Override
    public void assertVisible()
    {
    }

    @Override
    public void assertDisabled()
    {

    }

    @Override
    public void assertEnabled()
    {

    }

    @Override
    public Widget scrollTo()
    {
        return null;
    }

    @Override
    public void setStringValue(String value)
    {
        this.value = value;
    }

    @Override
    public String getStringValue()
    {
        return value;
    }
}

class ExampleComponentWithAComponentLikeMethodThatDoesNotReturnAComponent extends Component
{

    public ExampleComponentWithAComponentLikeMethodThatDoesNotReturnAComponent(Component parent)
    {
        super(parent);
    }

    public String notASubComponent()
    {
        return "I am not a sub-component";
    }

    @Override
    public void assertVisible()
    {
    }

    @Override
    public String getStringValue()
    {
        return null;
    }
}

class ExampleWidgetContainer extends Component implements WidgetContainer
{
    public ExampleInputComponent innerWidget1 = new ExampleInputComponent(mock(Component.class));
    public ExampleInputComponent innerWidget2 = new ExampleInputComponent(mock(Component.class));

    public ExampleWidgetContainer(Component parent)
    {
        super(parent);
    }

    @Override
    public Widget getWidget(String key)
    {
        if("Number1".equals(key))
        {
            return innerWidget1;
        }
        if("Number2".equals(key))
        {
            return innerWidget2;
        }
        return null;
    }

    @Override
    public void assertVisible()
    {
    }

    @Override
    public String getStringValue()
    {
        return null;
    }
}

class ExampleComponent extends Component
{
    public ExampleInputComponent subComponent1 = new ExampleInputComponent(mock(Component.class));
    public ExampleInputComponent subComponent2 = new ExampleInputComponent(mock(Component.class));
    public ExampleInputComponent privateComponent = new ExampleInputComponent(mock(Component.class));

    /**
     * Instantiates a new Component.
     *
     * @param parent the component containing this
     */
    public ExampleComponent(Component parent)
    {
        super(parent);
    }

    @Override
    public void assertVisible()
    {

    }

    public Component subComponent1()
    {
        return subComponent1;
    }

    public Component subComponent2()
    {
        return subComponent2;
    }

    private Component privateComponent()
    {
        return privateComponent;
    }

    public Component erroringMethod()
    {
        throw new RuntimeException("Bad stuff happened!");
    }

    @Override
    public String getStringValue()
    {
        return null;
    }
}