package it.uniroma2.partyflow.utilities.configurators;

import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.enums.ApplicationModalities;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Its responsibility is to obtain the application persistence layer
 */

public class PersistenceConfigurator {
    private final Properties properties = new Properties();
    private static PersistenceConfigurator instance = null;


    private PersistenceConfigurator() throws IOException {
        FileInputStream input = null;
        try{
            input = new FileInputStream("configurations/configPersistency.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }
        finally {
            if (input != null) {
                input.close();
            }
        }
    }

    // Guarantee Singleton
    public static PersistenceConfigurator getInstance() throws IOException {
        if(instance == null){
            instance = new PersistenceConfigurator();
        }

        return instance;
    }


    public PersistenceType getPersistenceType(){

        return switch (ApplicationModalities.valueOf(this.properties.getProperty("application.modalities"))){
            case ApplicationModalities.FULL -> PersistenceType.valueOf(this.properties.getProperty("persistence.type"));
            case ApplicationModalities.DEMO -> PersistenceType.InMemory;
        };
    }

    public List<String> getDBMSConnectionParameter() throws InvalidPersistenceTypeException {
        if( this.getPersistenceType() == PersistenceType.MySQL ) {
            List<String> DBMSConnproperties = new ArrayList<>();
            DBMSConnproperties.add(this.properties.getProperty("url.mysql"));
            DBMSConnproperties.add(this.properties.getProperty("username.mysql"));
            DBMSConnproperties.add(this.properties.getProperty("password.mysql"));
            return DBMSConnproperties;
        }

        else{
            throw new InvalidPersistenceTypeException("Persistence layer is not MySQL");
        }

    }


    public String getDirectoryCSVDB() throws InvalidPersistenceTypeException {
        if (this.getPersistenceType() == PersistenceType.CSV ){
            return this.properties.getProperty("directory.CSVDB");
        }
        else {
            throw new InvalidPersistenceTypeException("Persistence layer is not CSV");
        }
    }


}
