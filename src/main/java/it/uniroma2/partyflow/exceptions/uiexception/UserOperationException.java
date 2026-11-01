package it.uniroma2.partyflow.exceptions.uiexception;

/**
 * A UserOperationException indicates that an operation related
 * to user registration, authentication, or account management
 * has failed and must be handled by the UI.
 */

public class UserOperationException extends Exception {
    public UserOperationException() { super(); }
    public UserOperationException(String message) {
        super(message);
    }


}
