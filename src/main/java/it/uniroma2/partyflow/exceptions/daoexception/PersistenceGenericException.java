package it.uniroma2.partyflow.exceptions.daoexception;

/**
 * A generic exception that happens in the persistence layer.
 */

public class PersistenceGenericException extends Exception {

    public PersistenceGenericException() {
        super("Unexpected persistence error.");
    }

}
