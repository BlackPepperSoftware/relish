package com.aspenshore.relish.core;

import cucumber.deps.com.thoughtworks.xstream.annotations.XStreamConverter;

import java.util.*;

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
     * Except table row.
     *
     * @param except the except
     * @return the table row
     */
    public TableRow except(String... except) {
        List<String> exceptions = new ArrayList<>();
        for (String s : except) {
            exceptions.add((s.toUpperCase()));
        }
        Map<String, String> filteredMap = new HashMap<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (!exceptions.contains(entry.getKey().toUpperCase())) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new TableRow(filteredMap);
    }

    /**
     * Only table row.
     *
     * @param just the just
     * @return the table row
     */
    public TableRow only(String... just) {
        List<String> onlyThese = new ArrayList<>();
        for (String s : just) {
            onlyThese.add((s.toUpperCase()));
        }
        Map<String, String> filteredMap = new HashMap<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            if (onlyThese.contains(entry.getKey().toUpperCase())) {
                filteredMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new TableRow(filteredMap);
    }

    /**
     * Instantiates a new Table row.
     *
     * @param map the map
     */
    public TableRow(Map<String,String> map) {
        this.map = map;
    }

    /**
     * Get string.
     *
     * @param key the key
     * @return the string
     */
    public String get(String key) {
        return replaceExpressions(map.get(key));
    }

    public Map<String,Object> toObjectMap() {
        HashMap<String, Object> map = new HashMap<>();
        for (String key : this.map.keySet()) {
            map.put(key, get(key));
        }
        return map;
    }

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(String key, String value) {
        map.put(key, value);
    }

    /**
     * Entry set set.
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

