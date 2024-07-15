package com.musatov.ContentFilter;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class ArgsParser {
    @Parameter(names = "-o")
    private String outputPath;
    
    @Parameter(names = "-p")
    private String prefix;
    
    @Parameter(names = "-a")
    private boolean appendMode;
    
    @Parameter(names = "-s")
    private boolean shortStats = false;
    
    @Parameter(names = "-f")
    private boolean fullStats = false;
    
    @Parameter(description = "Input file paths")
    private List<String> inputFilePaths = new ArrayList<>();
    
    public boolean isAppendMode() {
        return appendMode;
    }
    
    public boolean isShortStats() {
        return shortStats;
    }
    
    public boolean isFullStats() {
        return fullStats;
    }
    
    public String getOutputPath() {
        return outputPath;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public List<String> getInputFilePaths() {
        return inputFilePaths;
    }
}
