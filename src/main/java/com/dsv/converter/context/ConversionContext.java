package com.dsv.converter.context;

import java.io.IOException;

import com.dsv.converter.service.Converter;


/**
 * The ConversionContext class represents a context in which a file conversion process is executed.
 * It holds a reference to a converter object, which is responsible for performing the actual file conversion.
 *
 * @author Dilraj vyas
 */
public class ConversionContext {
    private Converter converter;


    /**
     * This constructor creates a new ConversionContext object with a given converter.
     *
     * @param converter The converter object to be used for file conversion.
     */
    public ConversionContext(Converter converter) {
        this.converter = converter;
    }


    /**
     * This method executes the file conversion process by calling the convert method of the converter object.
     *
     * @param inputFile  The path of the input file to be converted.
     * @param outputFile The path of the output file to be created.
     * @throws IOException If there is an error reading or writing the files.
     */
    public void executeConversion(String inputFile, String outputFile, char delimiter) throws IOException {
        converter.convert(inputFile, outputFile, delimiter);
    }
}
