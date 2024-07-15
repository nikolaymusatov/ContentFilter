package com.musatov.ContentFilter;

import com.beust.jcommander.JCommander;

public class Main {
    public static void main(String[] args) {
        ArgsParser argsParser = new ArgsParser();
        JCommander.newBuilder().addObject(argsParser)
                .build().parse(args);
        FileProcessor fileProcessor = new FileProcessor(argsParser);
        fileProcessor.processFiles(argsParser.getInputFilePaths());
    }
    
}