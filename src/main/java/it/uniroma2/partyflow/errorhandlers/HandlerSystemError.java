package it.uniroma2.partyflow.errorhandlers;

import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HandlerSystemError {
    // Its responsibility is to handle errors that occur within the system.

    private static HandlerSystemError instance = null;
    private final Logger LOGGER =
            Logger.getLogger(Configurator.class.getName());

    private HandlerSystemError(){
        try{
            // Associates the log file with the logger.
            // The file will contain logs only for the current execution.
            FileHandler fileHandler =
                    new FileHandler("src/main/resources/it/uniroma2/partyflow/partyflow.log", false); // false means the existing file is overwritten

            fileHandler.setFormatter(new SimpleFormatter()); // sets the format of the log entries

            this.LOGGER.addHandler(fileHandler);
            this.LOGGER.setUseParentHandlers(false); // disable the default handlers
            //--------------------------------------------------------------------------------------------------------
        }
        catch (IOException e){
            // If the log file cannot be created or opened, the FileHandler is not added
            // and parent handlers remain enabled. Therefore, the logger falls back to
            // the default console handler and reports the error on the console.
            this.severeLog("IOException",e);
        }
    }

    // Guarantee Singleton
    public static HandlerSystemError getInstance(){
        if(instance == null){
            instance = new HandlerSystemError();
        }
        return instance;
    }

    public void severeLog(String msg, Throwable e){
        StackTraceElement[] stack = e.getStackTrace();

        String className = stack[0].getClassName();

        this.LOGGER.log(Level.SEVERE, className+": "+msg, e);
    }
}
