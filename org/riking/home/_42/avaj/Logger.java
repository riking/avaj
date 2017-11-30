package org.riking.home._42.avaj;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
    private static Logger instance;

    private PrintStream outStream;

    private Logger(String outputPath) throws FileNotFoundException, SecurityException {
        this.outStream = new PrintStream(outputPath);
    }

    /**
     * @return the currently in-use Logger instance
     */
    public static Logger getLogger() {
        if (instance == null) {
            throw new RuntimeException("Logger must be initialized before use.");
        }
        return instance;
    }

    /**
     * Initialize the logger to write to a file.
     *
     * @param outputPath Path to the logger output
     * @throws FileNotFoundException File could not be created
     * @throws SecurityException Write permission is denied
     */
    public static void setupFile(String outputPath) throws FileNotFoundException, SecurityException {
        if (instance != null) {
            instance.close();
            instance = null;
        }

        instance = new Logger(outputPath);
    }

    /**
     * Close and finalize the Logger.
     *
     * This must be called before the program exits.
     */
    public void close() {
        outStream.flush();
        if (outStream.checkError()) {
            System.err.println("An error occurred while writing the result.");
        }
        outStream.close();

        if (Logger.instance == this) {
            Logger.instance = null;
        }
    }

    /**
     * Get the output PrintStream.
     *
     * Do not close the returned PrintStream. Checking for errors is allowed but not required.
     *
     * @return PrintStream to use for output
     */
    public PrintStream getOutput() {
        return this.outStream;
    }
}
