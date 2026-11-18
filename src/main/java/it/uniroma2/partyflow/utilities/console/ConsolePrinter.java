package it.uniroma2.partyflow.utilities.console;

import java.io.PrintWriter;

/**
 * Singleton responsible for centralizing the management of CLI output.
 *
 * <p>Encapsulates access to the standard output stream ({@code System.out})
 * through a shared PrintWriter, avoiding direct calls to System.out
 * scattered throughout the application.</p>
 *
 * <p>This centralized approach simplifies debugging, troubleshooting,
 * and maintenance by providing a single entry point for managing
 * CLI output.</p>
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


    public static ConsolePrinter getInstance() {
        if (instance == null) {
            instance = new ConsolePrinter();
        }
        return instance;
    }

    /**
     * Prints the specified message followed by a line separator.
     *
     * @param message the message to print
     */
    public void println(String message) {
        writer.println(message);
    }

    /**
     * Prints a line separator without displaying any text.
     */
    public void println() {
        writer.println();
    }

}