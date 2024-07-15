package com.musatov.ContentFilter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileProcessor {
    private final String outputPath;
    private final String filePrefix;
    private final boolean appendMode;
    private final boolean shortStats;
    private final boolean fullStats;
    private boolean intFileCreated;
    private boolean floatFileCreated;
    private boolean stringFileCreated;
    private int intElements;
    private long minIntElement;
    private long maxIntElement;
    private long intSum;
    private int floatElements;
    private double floatSum;
    private double minFloatElement;
    private double maxFloatElement;
    private int stringElements;
    private int minStringSize;
    private int maxStringSize;
    
    public FileProcessor(ArgsParser argsParser) {
        this.outputPath = argsParser.getOutputPath();
        this.filePrefix = argsParser.getPrefix();
        this.appendMode = argsParser.isAppendMode();
        this.shortStats = argsParser.isShortStats();
        this.fullStats = argsParser.isFullStats();
    }
    
    public void processFiles(List<String> inputFiles) {
        for (String inputFile : inputFiles) {
            processFile(inputFile);
        }
        showStats();
    }
    
    private void processFile(String inputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                classifyAndWrite(line);
            }
        } catch (IOException e) {
            System.err.println("Error processing file " + inputFile + ": " + e.getMessage());
        }
    }
    
    private void classifyAndWrite(String line) throws IOException {
        Path integersFile = Paths.get(adaptPath(), adaptPrefix() + "integers.txt");
        Path floatsFile = Paths.get(adaptPath(), adaptPrefix() + "floats.txt");
        Path stringsFile = Paths.get(adaptPath(), adaptPrefix() + "strings.txt");
        if (isInteger(line)) {
            if (!intFileCreated && !appendMode) {
                Files.deleteIfExists(integersFile);
                intFileCreated = true;
            }
            writeToFile(integersFile, line + "\n");
            intElements++;
            updateIntsStats(line);
        } else if (isFloat(line)) {
            if (!floatFileCreated && !appendMode) {
                Files.deleteIfExists(floatsFile);
                floatFileCreated = true;
            }
            writeToFile(floatsFile, line + "\n");
            floatElements++;
            updateFloatsStats(line);
        } else {
            if (!stringFileCreated && !appendMode) {
                Files.deleteIfExists(stringsFile);
                stringFileCreated = true;
            }
            writeToFile(stringsFile, line + "\n");
            stringElements++;
            updateStringsStats(line);
        }
    }
    
    private boolean isInteger(String line) {
        return line.matches("^-?\\d+$");
    }
    
    private boolean isFloat(String line) {
        return line.matches("^-?\\d+([.]\\d+)?(E([-+])?\\d+)?$");
    }
    
    private void updateIntsStats(String line) {
        long value = Long.parseLong(line);
        if (minIntElement == 0) {
            minIntElement = value;
            maxIntElement = value;
        }
        maxIntElement = Math.max(value, maxIntElement);
        minIntElement = Math.min(value, minIntElement);
        intSum += value;
    }
    
    private void updateFloatsStats(String line) {
        double value = Double.parseDouble(line);
        if (minFloatElement == 0) {
            minFloatElement = value;
            maxFloatElement = value;
        }
        maxFloatElement = Math.max(value, maxFloatElement);
        minFloatElement = Math.min(value, minFloatElement);
        floatSum += value;
    }
    
    private void updateStringsStats(String line) {
        if (minStringSize == 0) {
            minStringSize = line.length();
            maxStringSize = line.length();
        } else {
            maxStringSize = Math.max(line.length(), maxStringSize);
            minStringSize = Math.min(line.length(), minStringSize);
        }
    }
    
    private void writeToFile(Path file, String line) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString(), true))) {
            writer.write(line);
        }
    }
    
    private String adaptPath() {
        return outputPath == null ? "." : outputPath;
    }
    
    private String adaptPrefix() {
        return filePrefix == null ? "" : filePrefix;
    }
    
    private void showStats() {
        System.out.println("Statistics:");
        if (fullStats) {
            System.out.println("\tIntegers: " + intElements);
            if (intElements > 0) {
                System.out.println("\t\tMin int value: " + minIntElement);
                System.out.println("\t\tMax int value: " + maxIntElement);
                System.out.println("\t\tAverage value: " + intSum / (float) intElements);
                System.out.println("\t\tSum: " + intSum);
            }
            System.out.println("\tFloats: " + floatElements);
            if (floatElements > 0) {
                System.out.println("\t\tMin float value: " + minFloatElement);
                System.out.println("\t\tMax float value: " + maxFloatElement);
                System.out.println("\t\tAverage value: " + floatSum / floatElements);
                System.out.println("\t\tSum: " + floatSum);
            }
            System.out.println("\tStrings: " + stringElements);
            if (stringElements > 0) {
                System.out.println("\t\tMin string length: " + minStringSize);
                System.out.println("\t\tMax string length: " + maxStringSize);
            }
        } else if (shortStats) {
            System.out.println("\tIntegers: " + intElements);
            System.out.println("\tFloats: " + floatElements);
            System.out.println("\tStrings: " + stringElements);
        }
    }
}
