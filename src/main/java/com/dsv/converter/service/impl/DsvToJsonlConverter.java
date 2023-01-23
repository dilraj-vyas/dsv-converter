package com.dsv.converter.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.dsv.converter.constant.DateTimePatternConstant;
import com.dsv.converter.model.Record;
import com.dsv.converter.service.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


/**
 * The DsvToJsonlConverter class implements the Converter interface and provides a concrete implementation
 * for converting a Delimiter-separated values (DSV) file to a JSONL file.
 * It uses the open-csv library to parse the DSV file and the Jackson library to convert the data to JSONL format.
 *
 * @author Dilraj vyas
 */
@Slf4j
public class DsvToJsonlConverter implements Converter {


    /**
     * Converts a file from one format to another using a specified delimiter.
     *
     * @param inputFile  the file to be converted
     * @param outputFile the file to which the converted content will be written
     * @param delimiter  the character to be used as a delimiter in the conversion process
     * @throws IOException if there is an error reading or writing to the input or output files
     */
    @Override
    public void convert(String inputFile, String outputFile, char delimiter) throws IOException {
        log.info("Started converting file {} to {}", inputFile, outputFile);
        Path inputPath = Paths.get(inputFile);
        CSVParser csvParser = CSVParser.parse(inputPath, StandardCharsets.UTF_8,
            CSVFormat.DEFAULT.withDelimiter(delimiter).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        String[] columnNames = csvParser.getHeaderMap().keySet().toArray(new String[0]);


        Stream<Record> dataStream = csvParser.getRecords().stream().parallel().map(record -> toData(record, columnNames));

        Path outputPath = Paths.get(outputFile);
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            dataStream.map(DsvToJsonlConverter::toJson).forEach(line -> {
                try {
                    writer.write(line);
                } catch (IOException e) {
                    log.error("Error while writing to file: {}", e.getMessage());
                }
            });
            log.info("File conversion completed successfully");
        }
    }

    /**
     * Converts a CSVRecord to a Record object using the specified column names.
     *
     * @param record      the CSVRecord to be converted
     * @param columnNames the names of the columns in the CSVRecord
     * @return a Record object created from the provided CSVRecord and column names
     */
    private static Record toData(CSVRecord record, String[] columnNames) {
        Map<String, Object> data = new HashMap<>();
        for (String columnName : columnNames) {
            String value = record.get(columnName);
            if (isValidDateFormat(value)) {
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATE_PATTERN_2);
                LocalDate date = null;
                try {
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATE_PATTERN);
                    date = LocalDate.parse(value, formatter1);
                } catch (DateTimeParseException e) {
                    try {
                        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATETIME_PATTERN_1);
                        date = LocalDate.parse(value, formatter2);
                    } catch (DateTimeParseException e1) {
                        try {
                            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATE_PATTERN_2);
                            date = LocalDate.parse(value, formatter3);
                        } catch (DateTimeParseException dateTimeParseException) {
                            log.error("Unable to parse the date strings: {}", e.getMessage());
                        }
                    }
                }
                data.put(columnName, date.format(outputFormatter));
            } else {
                data.put(columnName, value);
            }
        }
        return new Record(data);
    }


    /**
     * Validates whether the given value is in a valid date format.
     *
     * @param value the date value to be validated
     * @return true if the value is in a valid date format, false otherwise
     */
    private static boolean isValidDateFormat(String value) {
        // Define a pattern for the date formats
        String dateFormatPattern = "(\\d{2}[-/]\\d{2}[-/]\\d{4})|(\\d{4}[-/]\\d{2}[-/]\\d{2})";
        // Create a pattern object
        Pattern dateFormat = Pattern.compile(dateFormatPattern);
        // check if the value matches the pattern
        Matcher matcher = dateFormat.matcher(value);
        return matcher.matches();
    }

    /**
     * This method converts an Employee object to a JSON string.
     *
     * @param data The Employee object to be converted.
     * @return The JSON string representation of the Employee object.
     * @throws RuntimeException if there is an error converting the data to JSON format.
     */
    private static String toJson(Record data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(data) + System.lineSeparator();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting data to JSON format", e);
        }
    }
}
