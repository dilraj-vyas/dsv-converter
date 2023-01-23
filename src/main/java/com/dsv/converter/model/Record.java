package com.dsv.converter.model;


import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Record class represents a record in a CSV file. It contains a map of column names to values,
 * and provides methods for getting and setting the values of individual columns.
 *
 * @Data annotation is used to generate all the getters, setters, equals, hashCode, and toString methods
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Map<String, Object> columns = new HashMap<>();

    public Record(String[] columnNames, CSVRecord record) {
        for (String columnName : columnNames) {
            columns.put(columnName, record.get(columnName));
        }
    }

}
