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
 * Factory responsible for managing the database connection.
 *
 * <p>Provides centralized access to a shared JDBC connection,
 * lazily initialized on the first request using the connection
 * parameters provided by PersistenceConfigurator.</p>
 *
 * <p>All operations are static, and the private constructor
 * prevents instantiation of the class.</p>
 */

public class ConnectionFactory {

    private static Connection conn = null;

    private ConnectionFactory(){}


    /**
     * Returns the shared JDBC connection.
     *
     * <p>If the connection has not yet been established, retrieves
     * the database connection parameters from PersistenceConfigurator
     * and establishes a new connection through DriverManager.
     * Otherwise, returns the existing connection.</p>
     *
     * @return the JDBC database connection
     * @throws InvalidPersistenceTypeException if MySQL is not the
     * configured persistence type
     * @throws PersistenceServiceUnavailableException if the database connection
     * cannot be established or the configuration file cannot be loaded
     */

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


    /**
     * Closes the shared JDBC connection if it exists and is still open.
     *
     * <p>After successfully closing the connection, resets its reference
     * to null, allowing a new connection to be established on a subsequent
     * invocation of getConnection().</p>
     *
     * <p>Any SQLException occurring during this operation is logged
     * through HandlerSystemError without being propagated to the caller.</p>
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
