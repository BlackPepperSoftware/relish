package com.aspenshore.relish.core;

import java.util.HashMap;

/**
 * The type Getable map.
 */
public class GetableMap extends HashMap<String,String> implements Getable {
    /**
     * Instantiates a new Getable map.
     */
    public GetableMap() {
        super();
    }

    @Override
    public String get(String fieldName) {
        return super.get(fieldName);
    }
}