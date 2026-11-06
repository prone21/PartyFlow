package it.uniroma2.partyflow.exceptions.systemexception;

/**
 * Exception thrown when an unexpected internal error occurs
 * during the execution of the application.
 */

public class InternalException extends Exception {
    public InternalException(String message) {
        super(message);
    }
}
