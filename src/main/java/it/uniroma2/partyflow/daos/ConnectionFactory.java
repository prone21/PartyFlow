package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.errorhandlers.HandlerUIError;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Database connection factory using the <b>Singleton pattern (GoF)</b>.
 * <p>Maintains a single database connection for the application.
 */

public class ConnectionFactory {

    // ---------------singleton-----------------------
    private static ConnectionFactory instance = null;
    private static Connection conn = null;

    private ConnectionFactory(){}

    public static ConnectionFactory getInstance() throws PersistenceServiceUnavailableException{

        try{
            if (instance == null){
                PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
                List<String> connProp = pc.getDBMSConnectionParameter();
                conn = DriverManager.getConnection(connProp.get(0), connProp.get(1), connProp.get(2));
                instance = new ConnectionFactory();
            }

            return instance;
        }
        catch (InvalidPersistenceTypeException e){
            HandlerErrorFactory handlerErrorFactory = HandlerErrorFactory.getInstance();

            HandlerSystemError handlerSystemError = handlerErrorFactory.getHandlerSystemError();
            handlerSystemError.logError(e);

            HandlerUIError handlerUIError = handlerErrorFactory.getHandlerUIError();
            handlerUIError.errorMessage("An unexpected internal error has occurred. " +
                    "The application cannot continue and will be terminated.");

            handlerSystemError.terminateApp();

            return null;
        }
        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to establish database connection.",e);
            throw new PersistenceServiceUnavailableException();
        }

    }

    // -----------------------------------------------

    // returns the DB connection
    public Connection getConnection(){
        return conn;
    }

    /*
     * Closes the database connection.
     */
    public void closeConnection() {
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        if (conn != null) {
            try {
                conn.close();
                handlerSystemError.logInfo(("Database connection closed successfully"));
            } catch (SQLException e) {
                handlerSystemError.logError("Error closing database connection", e);
            }
        }
    }
}
