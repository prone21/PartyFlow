package it.uniroma2.partyflow.exceptions;

/**
 * Represents a fatal error that prevents the application from
 * continuing its execution safely.
 *
 * <p>Fatal errors indicate unrecoverable conditions that require
 * the application to terminate.</p>
 *
 * <p>All exceptions classified as fatal errors must extend this class.</p>
 */

public class FatalError extends Exception {
    public FatalError(String message) {
        super(message);
    }
}
