package it.uniroma2.partyflow.exceptions.daoexception;

import it.uniroma2.partyflow.exceptions.FatalError;

/**
 * An InvalidPersistenceTypeException indicates that an operation
 * cannot be performed because the configured persistence type
 * is incompatible with the persistence type required by the operation.
 */

public class InvalidPersistenceTypeException extends FatalError {
    public InvalidPersistenceTypeException(String message) {
        super(message);
    }
}
