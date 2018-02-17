package com.aspenshore.relish.core;

import java.util.HashMap;

public class GetableMap extends HashMap<String,String> implements Getable {
    public GetableMap() {
        super();
    }

    @Override
    public String get(String fieldName) {
        return super.get(fieldName);
    }
}