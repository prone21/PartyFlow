package it.uniroma2.partyflow.exceptions.daoexception;

/**
 * Exception thrown when a requested entity cannot be found
 * in the persistence layer.
 */

public class EntityNotFound extends Exception {

    public EntityNotFound(){super();}

    public EntityNotFound(String message) {
        super(message);
    }
}
