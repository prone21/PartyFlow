package it.uniroma2.partyflow.exceptions;

/**
 * An InvalidStateException indicates that an operation cannot be performed because
 * it is incompatible with the current state of the object.
 */

public class InvalidStateException extends Exception {
    public InvalidStateException(String message) {
        super(message);
    }

}
