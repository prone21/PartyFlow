package it.uniroma2.partyflow.errorhandlers;

import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerUIError;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerUIErrorFactory;
import it.uniroma2.partyflow.exceptions.FatalError;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Its responsibility is to handle errors that occur within the system.
 * It also coordinates the handling of fatal errors, including error
 * reporting and application termination.
 */

public class HandlerSystemError {

    private static HandlerSystemError instance = null;
    private final Logger logger =
            Logger.getLogger(HandlerSystemError.class.getName());

    private HandlerSystemError(){
        try{
            // Associates the log file with the logger.
            // The file will contain logs only for the current execution.
            FileHandler fileHandler =
                    new FileHandler("logs/partyflow.log", false); // false means the existing file is overwritten

            fileHandler.setFormatter(new SimpleFormatter()); // sets the format of the log entries

            this.logger.addHandler(fileHandler);
            this.logger.setUseParentHandlers(false); // disable the default handlers
            //--------------------------------------------------------------------------------------------------------
        }
        catch (IOException e){
            // If the log file cannot be created or opened, the FileHandler is not added
            // and parent handlers remain enabled. Therefore, the logger falls back to
            // the default console handler and reports the error on the console.
            this.logError("Unable to initialize the log file",e);
        }
    }

    // Guarantee Singleton
    public static HandlerSystemError getInstance(){
        if(instance == null){
            instance = new HandlerSystemError();
        }
        return instance;
    }


    /**
     * Logs an error including the original exception's stack trace.
     * An additional message can be provided to describe the context
     * in which the error occurred.
     *
     * @param message the error description
     * @param exception the exception associated with the error
     */

    public void logError(String message, Throwable exception) {
        this.logger.log(Level.SEVERE, message, exception);
    }

    /**
     * Logs an error message without an exception.
     *
     * @param message the error description
     */
    public void logError(String message) {
        this.logger.log(Level.SEVERE, message);
    }

    /**
     * Logs an error including the type, message, and stack trace of the exception.
     *
     * @param exception the exception associated with the error
     */
    public void logError(Throwable exception){
        this.logger.log(Level.SEVERE, "", exception);
    }


    /**
     * Logs a warning message.
     *
     * @param message the warning description
     */
    public void logWarning(String message) {
        this.logger.log(Level.WARNING, message);
    }


    /**
     * Logs an informational message.
     *
     * @param message the info description
     */
    public void logInfo(String message) {
        this.logger.log(Level.INFO, message);
    }

    /**
     * Handles unrecoverable errors and terminates the application.
     * The FatalError parameter restricts the operation to exceptions
     * explicitly classified as fatal.
     */
    public void handleFatalError(FatalError e) {

        this.logError("A fatal error occurred. The application will terminate.", e);

        HandlerUIErrorFactory handlerUIErrorFactory = HandlerUIErrorFactory.getInstance();
        HandlerUIError handlerUIError = handlerUIErrorFactory.getHandlerUIError();
        handlerUIError.errorMessage("An unexpected internal error has occurred. " +
                "The application cannot continue and will be terminated.");
        handlerUIError.terminateApplication();

    }
}
