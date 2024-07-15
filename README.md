# ContentFilter
In this project a small application for filtering files content is developed.

## Description
The utility is a small Java application that works with command line arguments. When you run the utilities on the command line, you are presented with several files contained in
mix integers, strings and real numbers. Line feed is used as a separator. Lines from files are read one by one according to their listing on the command line.  
The application task is to write different types of data to different files. Integers in one output file, real numbers in another, strings in a third. By default, files with the results of the result in the current form with the names integers.txt, floats.txt, strings.txt. Additionally, using the - o options, you need to be able to set the path to obtain the results. Option -p specifies the prefix of output file names. For example, -o /some/path -p result_ sets the output to the files /some/path/result_integers.txt, /some/path/result_strings.txt, etc.  
By default, the original results are reordered. Using the -a option, you can set the mode for adding files.

## Java version
The program is written and tested in Java version 21. However, Java version 8 is sufficient to build and run the utility.

## Build system
To build the project, the Maven build system version 3.9.7 is used.

## Third party libraries
This project uses the JCommander-1.82 library to work with command line arguments. Automatic installation of this dependency from the Maven repository is provided by the developer. No additional action is required.

## Building and running
The project is built into an executable jar file using the command `mvn clean package`. After that, a `target` directory will appear, which will contain `ContentFilter.jar` - an executable jar-file.   
The program is launched by executing the command `java -jar ContentFilter.jar [options] file1 file2 ...`, where the following flags can be used as `[options]`:
-a – adding data to existing files (append mode).
-o [path] – path to save output files.
-p [prefix] – prefix for output file names.
-s – brief statistics (only the number of elements).
-f – full statistics (minimum, maximum, sum, average for numbers; minimum and maximum length of strings).

## Usage examples
Running a program overwriting output files (default)
`java -jar ContentFilter.jar file1.txt file2.txt`

Running a program adding data to existing files
`java -jar ContentFilter.jar -a file1.txt file2.txt`

Run a program specifying the output path and prefix for the files
`java -jar ContentFilter.jar -o /some/path -p result_ file1.txt file2.txt`

Launching a program with brief statistics
`java -jar ContentFilter.jar -s file1.txt file2.txt`

Running the program with full statistics
`java -jar ContentFilter.jar -f file1.txt file2.txt`
