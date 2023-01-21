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
import java.util.stream.Stream;

import com.dsv.converter.constant.DateTimePatternConstant;
import com.dsv.converter.model.Employee;
import com.dsv.converter.service.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
     * This method converts a delimiter-separated values (DSV) file to a JSONL file.
     *
     * @param inputFile  The path of the DSV file to be converted.
     * @param outputFile The path of the JSONL file to be created.
     * @throws IOException If there is an error reading or writing the files.
     */
    @Override
    public void convert(String inputFile, String outputFile, char delimiter) throws IOException {

        log.info("Started converting file {} to {}", inputFile, outputFile);
        Path inputPath = Paths.get(inputFile);

        CSVParser csvParser = CSVParser.parse(inputPath, StandardCharsets.UTF_8,
            CSVFormat.DEFAULT.withDelimiter(delimiter).withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        Stream<Employee> dataStream = csvParser.getRecords().stream().parallel().map(DsvToJsonlConverter::toData);

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
     * This method converts a CSVRecord to an Employee object.
     *
     * @param record The CSVRecord to be converted.
     * @return The Employee object created from the CSVRecord.
     */
    private static Employee toData(CSVRecord record) {
        String firstName = record.get(0);
        String middleName = record.get(1);
        String lastName = record.get(2);
        String gender = record.get(3);
        LocalDate dob = null;
        try {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATE_PATTERN);
            dob = LocalDate.parse(record.get(4), formatter1);
        } catch (DateTimeParseException e) {
            try {
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATETIME_PATTERN_1);
                dob = LocalDate.parse(record.get(4), formatter2);
            } catch (DateTimeParseException e1) {
                try {
                    DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern(DateTimePatternConstant.DATE_PATTERN_2);
                    dob = LocalDate.parse(record.get(4), formatter3);
                } catch (DateTimeParseException dateTimeParseException) {
                    log.error("Unable to parse the date strings: {}", e.getMessage());
                }
            }
        }
        double salary = Integer.parseInt(record.get(5));
        return new Employee(firstName, middleName, lastName, gender, dob, salary);
    }


    /**
     * This method converts an Employee object to a JSON string.
     *
     * @param data The Employee object to be converted.
     * @return The JSON string representation of the Employee object.
     * @throws RuntimeException if there is an error converting the data to JSON format.
     */
    private static String toJson(Employee data) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writeValueAsString(data) + System.lineSeparator();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting data to JSONL format", e);
        }
    }
}
