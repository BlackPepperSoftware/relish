package com.aspenshore.relish.core;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import java.util.*;

/**
 * The type Table row.
 */
@XStreamConverter(TableRowConverter.class)
public class TableRow implements Getable {
    private final Map<String,String> map;

    /**
     * Instantiates a new Table row.
     */
    public TableRow() {
        this.map = new HashMap<>();
    }

    /**
     * Return a cut-down version of the row, excluding the specified columns.
     *
     * @param columnNames the except
     * @return the table row
     */
    public TableRow except(String... columnNames) {
        List<String> exceptions = new ArrayList<>();
        for (String columnName : columnNames) {
            exceptions.add(columnName.toUpperCase(Locale.ENGLISH));
        }
        Map<String, String> filteredMap = new HashMap<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (!exceptions.contains(entry.getKey().toUpperCase(Locale.ENGLISH))) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new TableRow(filteredMap);
    }

    /**
     * Return a cut-down version of this row, containing only the specified columns.
     *
     * @param columnNames the only columns to include
     * @return row containing only the specified columns
     */
    public TableRow only(String... columnNames) {
        List<String> onlyThese = new ArrayList<>();
        for (String columnName : columnNames) {
            onlyThese.add(columnName.toUpperCase(Locale.ENGLISH));
        }
        Map<String, String> filteredMap = new HashMap<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (onlyThese.contains(entry.getKey().toUpperCase(Locale.ENGLISH))) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new TableRow(filteredMap);
    }

    /**
     * Instantiates a new Table row.
     *
     * @param map containing values for the row
     */
    public TableRow(Map<String,String> map) {
        this.map = map;
    }

    /**
     * Get a row value by name.
     *
     * @param key the key
     * @return the string
     */
    public String get(String key) {
        return replaceExpressions(map.get(key));
    }

    /**
     * Convert the table-row to a map.
     *
     * @return the map
     */
    public Map<String,Object> toObjectMap() {
        HashMap<String, Object> clonedMap = new HashMap<>();
        for (String key : this.map.keySet()) {
            clonedMap.put(key, get(key));
        }
        return clonedMap;
    }

    /**
     * Put a new value into the row.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, String value) {
        map.put(key, value);
    }

    /**
     * Entries in this row.
     *
     * @return the set
     */
    public Set<Map.Entry<String,String>> entrySet() {
        return map.entrySet();
    }

    private String replaceExpressions(String value) {
        return value;
    }
}

