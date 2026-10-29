package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.errorhandlers.HandlerCLIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.InvalidStateException;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

//TODO ricordati che devi confrontarti con elia, col suo file che si chiama ConnectionFactory e l'altro UserDaoMySql

public class ConnectionFactory {
    /**
     * Database connection factory using the <b>Singleton pattern (GoF)</b>.
     * <p>Maintains a single database connection for the application.
     */

    // ---------------singleton-----------------------
    private static ConnectionFactory instance = null;
    private static Connection conn;

    private ConnectionFactory(){}

    public static ConnectionFactory getInstance(){
        if (instance == null){

            try{
                PersistenceConfigurator pc = PersistenceConfigurator.getInstance();
                List<String> connProp = pc.getDBMSConnectionParameter();
                conn = DriverManager.getConnection(connProp.get(0), connProp.get(1), connProp.get(2));
                instance = new ConnectionFactory();
            }
            catch (InvalidStateException | SQLException e){
                //TODO non basta che logghi secondo me, cioè è importante se magari non si crea la connessione
                //TODO forse dovresti rivedere anche il fatto di avere un solo catch
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e);
            }

        }

        return instance;
    }
    // -----------------------------------------------

    // returns the DB connection
    public Connection getConnection(){
        return conn;
    }
}
