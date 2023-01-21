package com.dsv.converter;

import com.dsv.converter.context.ConversionContext;
import com.dsv.converter.service.Converter;
import com.dsv.converter.service.impl.DsvToJsonlConverter;

public class Main {
    public static void main(String[] args) {

        try {
            String inputFile = args[0];
            String outputFile = args[1];
            char delimiter = args[2].charAt(0);
            Converter dsvToJsonlConverter = new DsvToJsonlConverter();
            ConversionContext context = new ConversionContext(dsvToJsonlConverter);
            long startTime = System.currentTimeMillis();

            context.executeConversion(inputFile, outputFile, delimiter);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println("Time taken: " + timeTaken / 1000 + " seconds");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}