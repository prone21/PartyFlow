package it.uniroma2.partyflow.exceptions.systemexception;


/**
 * Unchecked exception used to interrupt the application execution
 * flow when an unrecoverable fatal error occurs.
 *
 * <p>This exception is thrown by the terminateApplication() method
 * of the active UI error handler.</p>
 *
 * <p>In the CLI application, it allows the call stack to unwind
 * until it is caught by CLIApplication.launch(), ensuring that
 * the finally block is executed and resources are released.</p>
 *
 * <p>In the JavaFX application, it interrupts the current execution
 * flow after Platform.exit() has requested the shutdown of the
 * JavaFX runtime. Resource cleanup is performed by GUIApplication.stop().</p>
 *
 * <p>Extending RuntimeException allows the exception to propagate
 * without requiring explicit throws declarations in intermediate
 * methods.</p>
 */

public class ApplicationTerminationException extends RuntimeException {
    public ApplicationTerminationException() {
        super("Application terminated due to a fatal error.");
    }
}
