package it.uniroma2.partyflow.exceptions.daoexception;

/**
 * Raised when a persistence operation violates a rule enforced by the selected storage
 * implementation.
 */

public class PersistenceConstraintViolationException extends Exception {

    public PersistenceConstraintViolationException() {
        super("Persistence constraint violated.");
    }

    public PersistenceConstraintViolationException(String message) {
        super(message);
    }

}
