package it.uniroma2.partyflow.errorhandlers.handlerui;


import it.uniroma2.partyflow.enums.UserField;
import it.uniroma2.partyflow.exceptions.systemexception.ApplicationTerminationException;
import it.uniroma2.partyflow.utilities.ConsolePrinter;


/**
 * Error handler responsible for managing errors in the command-line interface.
 *
 * <p>Implements the Singleton pattern and provides methods for displaying
 * error messages and identifying missing mandatory fields.</p>
 *
 * <p>Handles application termination by throwing an
 * {@link ApplicationTerminationException}, respecting the CLI application
 * lifecycle and allowing the launcher to perform resource cleanup.</p>
 */


public class HandlerCLIError implements HandlerUIError {
    private static HandlerCLIError instance = null;
    private final ConsolePrinter printer = ConsolePrinter.getInstance();

    private HandlerCLIError(){}

    public static HandlerCLIError getInstance(){
        if(instance == null){
            instance = new HandlerCLIError();
        }
        return instance;
    }


    /**
     * Displays an error message on the command-line interface,
     * allowing the user to identify the reason for the failure.
     *
     * @param message the error message to display
     */

    @Override
    public void errorMessage(String message) {
        printer.println("Error: " + message);
    }


    /**
     * Requests termination of the CLI application by throwing
     * an unchecked exception.
     *
     * <p>Allows the call stack to unwind and the application launcher
     * to perform the necessary resource cleanup.</p>
     *
     * @throws ApplicationTerminationException always, to interrupt
     * the current execution flow
     */

    @Override
    public void terminateApplication() {
        throw new ApplicationTerminationException();
    }


    /**
     * Displays an error message identifying a missing mandatory field
     * and prompts the user to try again.
     *
     * @param field the mandatory field that has not been provided
     */

    public void printRequiredFieldError(UserField field) {

        String fieldName = switch (field) {
            case NAME -> "Name";
            case SURNAME -> "Surname";
            case DATE_OF_BIRTH -> "Date of birth";
            case EMAIL -> "Email";
            case PASSWORD -> "Password";
            case GENDER -> "Gender";
            case ACCOUNT_TYPE -> "Account type";
        };

        printer.println("Error: " + fieldName + " is required.");
        printer.println();
        printer.println("Please try again.");
        printer.println();
    }
}
