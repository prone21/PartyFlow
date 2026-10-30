package it.uniroma2.partyflow.exceptions.uiexception;

/**
 * Thrown when a field contains a value that does not comply
 * with the expected format or validation constraints,
 * regardless of how the value was provided.
 */

public class InvalidFieldFormatException extends Exception {
    public InvalidFieldFormatException(String message){
        super(message);
    }

}
