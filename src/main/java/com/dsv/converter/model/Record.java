package com.dsv.converter.model;


import org.apache.commons.csv.CSVRecord;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
