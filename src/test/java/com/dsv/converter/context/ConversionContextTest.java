package com.dsv.converter.context;


import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dsv.converter.service.impl.DsvToJsonlConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionContextTest {

    private ConversionContext conversionContext;

    @Before
    public void setUp() {
        conversionContext = new ConversionContext(new DsvToJsonlConverter());
    }

    @Test
    public void testExecuteConversionTest1() throws IOException {
        String inputFile = "src/main/resources/files/DSV_input1.txt";
        String outputFile = "src/main/resources/files/output.json";
        char delimiter = ',';
        conversionContext.executeConversion(inputFile, outputFile, delimiter);
        File output = new File(outputFile);
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(outputFile);
        JsonNode jsonNode = mapper.readTree(jsonFile);
        Assert.assertTrue(output.exists() && output.isFile() && output.canRead());
        Assert.assertNotNull(jsonNode);
    }

    @Test
    public void testExecuteConversionTest2() throws IOException {
        String inputFile = "src/main/resources/files/DSV_input2.txt";
        String outputFile = "src/main/resources/files/output1.json";
        char delimiter = '|';
        conversionContext.executeConversion(inputFile, outputFile, delimiter);
        File output = new File(outputFile);
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(outputFile);
        JsonNode jsonNode = mapper.readTree(jsonFile);
        Assert.assertTrue(output.exists() && output.isFile() && output.canRead());
        Assert.assertNotNull(jsonNode);
    }

    @Test
    public void testExecuteConversionTest3() throws IOException {
        String inputFile = "src/main/resources/files/DSV_input3.txt";
        String outputFile = "src/main/resources/files/output3.json";
        char delimiter = ',';
        conversionContext.executeConversion(inputFile, outputFile, delimiter);
        File output = new File(outputFile);
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(outputFile);
        JsonNode jsonNode = mapper.readTree(jsonFile);
        Assert.assertTrue(output.exists() && output.isFile() && output.canRead());
        Assert.assertNotNull(jsonNode);
    }

}
