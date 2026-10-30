package it.uniroma2.partyflow.exceptions.uiexception;

import it.uniroma2.partyflow.enums.UserField;

/**
 * Thrown when a required field is missing or contains an empty value,
 * regardless of how the value was provided.
 */

public class RequiredFieldException extends Exception{
    private final UserField field;
    public RequiredFieldException(UserField field){
        super();
        this.field = field;
    }

    public UserField getField(){
        return this.field;
    }
}
