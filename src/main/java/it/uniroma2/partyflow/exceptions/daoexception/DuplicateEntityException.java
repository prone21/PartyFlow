package it.uniroma2.partyflow.exceptions.daoexception;

/**
 * Raised when an attempt is made to persist an entity that already exists according to its identifying
 * key or applicable uniqueness rule
 */

public class DuplicateEntityException extends Exception{

    public DuplicateEntityException() {
        super("Duplicate entity detected during persistence.");
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

}
