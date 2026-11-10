package it.uniroma2.partyflow.exceptions.systemexception;

public class ApplicationTerminationException extends RuntimeException {
    public ApplicationTerminationException() {
        super("Application terminated due to a fatal error.");
    }
}
