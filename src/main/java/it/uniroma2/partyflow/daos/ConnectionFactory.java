package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Factory responsible for managing a database connection.
 *
 * <p>Provides centralized access to a single JDBC connection,
 * lazily initialized on the first request and reused by
 * subsequent calls until explicitly closed.</p>
 *
 * <p>All operations are static; no instance of this class
 * can be created.</p>
 */

public class ConnectionFactory {

    private static Connection conn = null;

    private ConnectionFactory(){}

    // returns the DB connection
    public static Connection getConnection()  throws InvalidPersistenceTypeException, PersistenceServiceUnavailableException {
        try{
            if(conn == null){
                PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
                List<String> connProp = pc.getDBMSConnectionParameter();
                conn = DriverManager.getConnection(connProp.get(0), connProp.get(1), connProp.get(2));
            }
        }
        catch (InvalidPersistenceTypeException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }
        catch (SQLException | IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to establish database connection.",e);
            throw new PersistenceServiceUnavailableException();
        }

        return conn;
    }

    /*
     * Closes the database connection.
     */
    public static void closeConnection() {
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        try{
            if(conn != null && !conn.isClosed()){
                conn.close();
                conn = null;
                handlerSystemError.logInfo(("Database connection closed successfully"));
            }
        }
        catch (SQLException e) {
            handlerSystemError.logError("Error closing database connection", e);
        }

    }

}
