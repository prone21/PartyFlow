package it.uniroma2.partyflow.exceptions.uiexception;

public class PersistenceError extends Exception {
    public PersistenceError(String message) {
        super(message);
    }
    public PersistenceError() {
        super();
    }
}
