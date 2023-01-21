package com.dsv.converter.service;

import java.io.IOException;


/**
 * The Converter interface defines a contract for file conversion operations.
 * Classes implementing this interface must provide an implementation for the convert method.
 *
 * @author Dilraj vyas
 */
public interface Converter {

    /**
     * This method converts a file from one format to another.
     *
     * @param inputFile  The path of the input file to be converted.
     * @param outputFile The path of the output file to be created.
     * @throws IOException If there is an error reading or writing the files.
     */
    void convert(String inputFile, String outputFile, char delimiter) throws IOException;
}
