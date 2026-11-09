package it.uniroma2.partyflow.exceptions.uiexception;

/**
 * Represents a recoverable application-level exception.
 *
 * It is used to communicate failures occurring during the
 * execution of application use cases, providing meaningful
 * error messages to the presentation layer.
 */

public class ApplicationException extends Exception {
    public ApplicationException() { super(); }
    public ApplicationException(String message) {
        super(message);
    }


}
