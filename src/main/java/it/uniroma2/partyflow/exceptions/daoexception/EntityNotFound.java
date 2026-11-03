package it.uniroma2.partyflow.exceptions.daoexception;

public class EntityNotFound extends Exception {

    public EntityNotFound(){super();}

    public EntityNotFound(String message) {
        super(message);
    }
}
