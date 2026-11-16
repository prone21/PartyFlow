package it.uniroma2.partyflow.exceptions.systemexception;

/**
 * Unchecked exception used to terminate the CLI application
 * when an unrecoverable fatal error occurs.
 *
 * <p>Unlike System.exit(), throwing this exception allows the
 * execution stack to unwind normally, ensuring that the finally
 * block in CliApplication.launch() is executed.</p>
 *
 * <p>The exception is thrown by HandlerCLIError.terminateApplication()
 * and propagates through the call stack until it is caught by
 * CliApplication.launch(). This mechanism interrupts the current
 * execution flow while allowing the launcher to invoke close()
 * and perform the necessary cleanup operations, such as closing
 * the database connection.</p>
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
