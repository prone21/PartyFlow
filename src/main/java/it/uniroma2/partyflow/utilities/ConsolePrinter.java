package it.uniroma2.partyflow.utilities;

import java.io.PrintWriter;

/**
 * Singleton responsible for managing CLI output.
 *
 * <p>Provides centralized console output using a PrintWriter.</p>
 */
public class ConsolePrinter {

    private static ConsolePrinter instance;

    private final PrintWriter writer;

    /*
     * PrintWriter: writes formatted text to an output destination.
     * System.out: represents the standard output stream (console).
     * Auto-flush: automatically flushes the buffer after println() or printf().
     */

    private ConsolePrinter() {
        // Creates a PrintWriter that writes to standard output.
        // true enables auto-flush after println(), printf() or format().
        this.writer = new PrintWriter(System.out, true);
    }

    /**
     * Returns the unique instance of CLIWriter.
     * Creates it on the first invocation (lazy initialization).
     */
    public static ConsolePrinter getInstance() {
        if (instance == null) {
            instance = new ConsolePrinter();
        }
        return instance;
    }

    /**
     * Prints a message followed by a new line.
     */
    public void println(String message) {
        writer.println(message);
    }

    /**
     * Prints an empty line.
     */
    public void println() {
        writer.println();
    }

}