package it.uniroma2.partyflow.utilities.configurators;

import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.enums.ApplicationMode;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Singleton responsible for managing the application persistence configuration.
 *
 * <p>Loads persistence properties from a configuration file,
 * determines the persistence type according to the application mode,
 * and provides the parameters required to access the persistence layer.</p>
 */


public class PersistenceConfigurator {
    private final Properties properties = new Properties();
    private static PersistenceConfigurator instance = null;


    private PersistenceConfigurator() throws IOException {
        try (FileInputStream input = new FileInputStream("configurations/configPersistency.ini")){
            this.properties.load(input);
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }
    }

    // Guarantee Singleton
    public static PersistenceConfigurator getInstance() throws IOException {
        if(instance == null){
            instance = new PersistenceConfigurator();
        }

        return instance;
    }


    /**
     * Determines the persistence type according to the application mode.
     *
     * <p>In FULL mode, the persistence type is obtained from the
     * configuration file. In DEMO mode, in-memory persistence
     * is automatically selected.</p>
     *
     * @return the active persistence type
     */

    public PersistenceType getPersistenceType(){

        return switch (ApplicationMode.valueOf(this.properties.getProperty("application.modalities"))){
            case ApplicationMode.FULL -> PersistenceType.valueOf(this.properties.getProperty("persistence.type"));
            case ApplicationMode.DEMO -> PersistenceType.IN_MEMORY;
        };
    }


    /**
     * Retrieves the connection parameters required to access the MySQL database.
     *
     * @return a list containing the database URL, username, and password,
     * in this order
     * @throws InvalidPersistenceTypeException if MySQL is not the active persistence type
     */

    public List<String> getDBMSConnectionParameter() throws InvalidPersistenceTypeException {
        if( this.getPersistenceType() == PersistenceType.MY_SQL ) {
            List<String> dbmsConnProperties = new ArrayList<>();
            dbmsConnProperties.add(this.properties.getProperty("url.mysql"));
            dbmsConnProperties.add(this.properties.getProperty("username.mysql"));
            dbmsConnProperties.add(this.properties.getProperty("password.mysql"));
            return dbmsConnProperties;
        }

        else{
            throw new InvalidPersistenceTypeException("Persistence layer is not MySQL");
        }

    }



    /**
     * Retrieves the directory where the CSV database files are stored.
     *
     * @return the configured CSV database directory
     * @throws InvalidPersistenceTypeException if CSV is not the active persistence type
     */

    public String getDirectoryCSVDB() throws InvalidPersistenceTypeException {
        if (this.getPersistenceType() == PersistenceType.CSV ){
            return this.properties.getProperty("directory.CSVDB");
        }
        else {
            throw new InvalidPersistenceTypeException("Persistence layer is not CSV");
        }
    }


}
