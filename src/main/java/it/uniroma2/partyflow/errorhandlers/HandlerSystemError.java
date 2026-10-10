package it.uniroma2.partyflow.errorhandlers;

import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HandlerSystemError {
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
            this.LOGGER.setUseParentHandlers(false);
            //--------------------------------------------------------------------------------------------------------
        }
        catch (IOException e){
            this.severeLog("IOException",e);
        }
    }

    public static HandlerSystemError getInstance(){
        if(instance == null){
            instance = new HandlerSystemError();
        }
        return instance;
    }

    public void severeLog(String msg, Throwable e){
        this.LOGGER.log(Level.SEVERE, msg, e);
    }
}
