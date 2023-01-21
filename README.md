# dsv-converter
- Create a Maven project that converts DSV (Delimiter-separated values) files into JSONL (JSON line) format.
- The test input files and expected output file are included.

# Requirements:
- Dates in JSONL output file must be in YYYY-MM-dd format.
- The project can be built using Maven in a terminal, e.g. with "mvn package".    
- Any DSV file with any arbitrary data should be convertible with this tool. This means the test files are only examples, the real structure of the data at runtime should be dynamic.
- An executable JAR is created to allow user to run the conversion in a terminal.
- The user can specify the input file and any additional parameters if necessary via command-line arguments.
- Both reading input and writing output files should be done in a streaming manner:
  - You must never store all data entries in memory at the same time (Expect millions of entries in a real use case).
  - You should use Java Stream API for stream processing.
- Unit tests with either JUnit 4 or 5 should be created for the project. Both of the provided test input files should be used in the tests.


# Command to Run Application

  `mvn clean install`
  
  `mvn package`  
  
 `java -jar target/dsv-converter-1.0-SNAPSHOT.jar DSV_input1.txt output.json ',' `

