package it.uniroma2.partyflow.exceptions.uiexception;

/**
 * A UserOperationException indicates that an operation related
 * to user registration, authentication, or account management
 * has failed and must be handled by the UI.
 */

public class ApplicationException extends Exception {
    public ApplicationException() { super(); }
    public ApplicationException(String message) {
        super(message);
    }


}
