package it.uniroma2.partyflow.errorhandlers.handlerui;

/**
 * Defines a common interface for handling errors across different
 * user interfaces (JavaFX GUI and CLI).
 *
 * <p>Each implementation is responsible for displaying error messages
 * and requesting application termination according to its specific
 * execution environment.</p>
 *
 * <p>The {@link #terminateApplication()} method must respect the lifecycle
 * of the JavaFX or CLI application, depending on the active interface.
 * It must not call {@code System.exit()} directly.</p>
 *
 */


public interface HandlerUIError {
    void errorMessage(String mex);
    void terminateApplication();
}
