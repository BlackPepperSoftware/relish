package com.aspenshore.relish.core;

import java.util.HashMap;
import java.util.Map;

import cucumber.deps.com.thoughtworks.xstream.converters.Converter;
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter;


/**
 * The type Table row converter.
 */
public class TableRowConverter implements Converter {

    @Override
    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        TableRow tableRow = (TableRow) obj;
        for (Map.Entry<String,String> entry: tableRow.entrySet()) {
            writer.startNode(entry.getKey());
            writer.setValue(entry.getValue());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        HashMap<String, String> map = new HashMap<>();
        while(reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            String value = reader.getValue();
            map.put(nodeName, value);
            reader.moveUp();
        }
        return new TableRow(map);
    }

    @Override
    public boolean canConvert(Class aClass) {
        return TableRow.class.isAssignableFrom(aClass);
    }
}
