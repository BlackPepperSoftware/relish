package com.aspenshore.relish.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;


/**
 * The type Table row matchers.
 */
public class TableRowMatchers {
    private TableRowMatchers() {
        // Should not be instantiated.
    }

    /**
     * Bean matches all matcher.
     *
     * @param tableRow the table row
     * @return the matcher
     */
    public static Matcher beanMatchesAll(TableRow tableRow) {
        return new ItemRowBeanMatcher(tableRow);
    }

    /**
     * Gets matches all.
     *
     * @param tableRow the table row
     * @return the matches all
     */
    public static Matcher<Getable> getableMatchesAll(TableRow tableRow) {
        return new ItemRowGetableMatcher(tableRow);
    }

    private static class ItemRowBeanMatcher extends BaseMatcher {

        private final Matcher matcher;

        /**
         * Instantiates a new Item row bean matcher.
         *
         * @param tableRow the table row
         */
        ItemRowBeanMatcher(TableRow tableRow) {
            List<Matcher<Object>> result = new ArrayList<>();
            for (Map.Entry<String,String> entry : tableRow.entrySet()) {
                Matcher<Object> objectMatcher = hasProperty(entry.getKey(), equalTo(entry.getValue()));
                result.add(objectMatcher);
            }
            matcher = allOf(result.toArray(new Matcher[result.size()]));
        }

        @Override
        public boolean matches(Object o) {
            return matcher.matches(o);
        }

        @Override
        public void describeTo(Description description) {
            matcher.describeTo(description);
        }
    }

    private static class ItemRowGetableMatcher extends BaseMatcher<Getable> {

        private final Matcher matcher;
        private final TableRow tableRow;

        /**
         * Instantiates a new Item row getable matcher.
         *
         * @param tableRow the table row
         */
        ItemRowGetableMatcher(TableRow tableRow) {
            this.tableRow = tableRow;
            List<GetableMatcher> result = new ArrayList<>();
            for (Map.Entry<String,String> entry : tableRow.entrySet()) {
                GetableMatcher objectMatcher = hasGetableValue(entry.getKey(), equalTo(entry.getValue()));
                result.add(objectMatcher);
            }
            matcher = allOf(result.toArray(new Matcher[result.size()]));
        }

        @Override
        public boolean matches(Object obj) {
            Getable getable = (Getable) obj;
            return matcher.matches(getable);
        }

        @Override
        public void describeMismatch(Object item, Description description) {
            final Getable getable = (Getable) item;
            StringBuilder result = new StringBuilder();
            for (Map.Entry<String,String> e : tableRow.entrySet()) {
                result.append("match '")
                        .append(e.getKey())
                        .append("' with '")
                        .append(getable.get(e.getKey()))
                        .append("' and ");
            }
            String actualDesc = result.toString();
            description.appendText("was ").appendValue(actualDesc);
        }

        @Override
        public void describeTo(Description description) {
            matcher.describeTo(description);
        }

        @Override
        public String toString() {
            return "ItemRowGetableMatcher: " + super.toString();
        }
    }

    private static GetableMatcher hasGetableValue(String keyName, Matcher matcher) {
        return new GetableMatcher(keyName, matcher);
    }

    private static class GetableMatcher extends TypeSafeDiagnosingMatcher<Getable> {

        private final String key;
        private final Matcher keyMatcher;

        /**
         * Instantiates a new Getable matcher.
         *
         * @param key        the key
         * @param keyMatcher the key matcher
         */
        GetableMatcher(String key, Matcher keyMatcher) {
            this.key = key;
            this.keyMatcher = keyMatcher;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("match '" + key + "' with ");
            keyMatcher.describeTo(description);
        }

        @Override
        protected boolean matchesSafely(Getable getable, Description description) {
            return keyMatcher.matches(getable.get(key));
        }

        @Override
        public String toString() {
            return "GetableMatcher: " + super.toString();
        }
    }

}
