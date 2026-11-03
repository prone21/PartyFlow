package it.uniroma2.partyflow.exceptions.daoexception;

/**
 * Raised when the configured persistence backend cannot be accessed or used.
 */

public class PersistenceServiceUnavailableException extends Exception {

    public PersistenceServiceUnavailableException() {
        super("Persistence service unavailable.");
    }

    public PersistenceServiceUnavailableException(String message) {
        super(message);
    }

}
