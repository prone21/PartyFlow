package it.uniroma2.partyflow.exceptions;

import it.uniroma2.partyflow.enums.UserField;

/**
 * Thrown when a required Bean attribute is assigned
 * a null or empty value through its setter.
 */

public class RequiredFieldException extends Exception{
    UserField field;
    public RequiredFieldException(UserField field){
        super();
        this.field = field;
    }

    public UserField getField(){
        return this.field;
    }
}
