package it.uniroma2.partyflow.utilities;

import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersistenceConfigurator {
    // Its responsibility is to obtain the persistence layer
    private final Properties properties = new Properties();
    private static PersistenceConfigurator instance = null;


    private PersistenceConfigurator(){
        try{
            FileInputStream input =
                    new FileInputStream("src/main/resources/it/uniroma2/partyflow/configPersistency.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerErrorFactory hef = HandlerErrorFactory.getInstance();
            HandlerSystemError hse = hef.getHandlerSystemError();
            hse.severeLog("IOException", e);

        }
    }

    // Guarantee Singleton
    public static PersistenceConfigurator getInstance(){
        if(instance == null){
            instance = new PersistenceConfigurator();
        }

        return instance;
    }


    public PersistenceType getPersistenceType(){
        return PersistenceType.valueOf(this.properties.getProperty("persistence.type"));
    }


    // An IllegalStateException indicates that an operation
    // has been attempted that is inconsistent with the current state of an object.

    public List<String> getDBMSConnectionParameter() throws IllegalStateException{
        if( this.getPersistenceType() == PersistenceType.MySQL ) {
            List<String> DBMSConnproperties = new ArrayList<>();
            DBMSConnproperties.add(this.properties.getProperty("url.mysql"));
            DBMSConnproperties.add(this.properties.getProperty("username.mysql"));
            DBMSConnproperties.add(this.properties.getProperty("password.mysql"));
            DBMSConnproperties.add(this.properties.getProperty("url.mysql"));
            return DBMSConnproperties;
        }

        else{
            throw new IllegalStateException("Persistence layer is not MySQL");
        }

    }


    public String getDirectoryCSVDB() throws IllegalStateException{
        if (this.getPersistenceType() == PersistenceType.CSV ){
            return this.properties.getProperty("directoryCSVDB");
        }
        else {
            throw new IllegalStateException("Persistence layer is not CSV");
        }
    }


}
