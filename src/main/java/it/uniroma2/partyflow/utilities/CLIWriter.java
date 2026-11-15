package it.uniroma2.partyflow.utilities;

import java.io.PrintWriter;

/**
 * Singleton responsible for managing CLI output.
 *
 * <p>Provides centralized access to the standard output
 * through a PrintWriter.</p>
 *
 * <p>Implements the Singleton pattern (GoF) using
 * lazy initialization.</p>
 */
public final class CLIWriter {

    private static CLIWriter instance;

    private final PrintWriter writer;

    private CLIWriter() {
        this.writer = new PrintWriter(System.out, true);
    }

    public static CLIWriter getInstance() {
        if (instance == null) {
            instance = new CLIWriter();
        }
        return instance;
    }

    public void print(String message) {
        writer.print(message);
        writer.flush();
    }

    public void println(String message) {
        writer.println(message);
    }

    public void println() {
        writer.println();
    }

    public void printf(String format, Object... args) {
        writer.printf(format, args);
        writer.flush();
    }
}