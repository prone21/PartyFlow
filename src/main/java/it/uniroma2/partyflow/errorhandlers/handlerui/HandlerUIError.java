package it.uniroma2.partyflow.errorhandlers.handlerui;

/**
 * Defines a common interface for handling errors across different
 * user interfaces (JavaFX GUI and CLI).
 * Each implementation is responsible for displaying error messages
 * and requesting application termination according to its specific
 * execution environment.
 * terminateApplication() method must respect the lifecycle of the
 * JavaFX or CLI application, depending on the active interface.
 * It should not simply call System.exit(0).
 */

public interface HandlerUIError {
    void errorMessage(String mex);
    void terminateApplication();
}
