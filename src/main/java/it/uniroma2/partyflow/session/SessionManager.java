package it.uniroma2.partyflow.session;
import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class SessionManager {
    private static SessionManager instance = null;
    private static Connection conn;

    private SessionManager(){};

    public static SessionManager getSessionManager() throws SQLException {
        if (instance == null){

            try{
                 Configurator c = Configurator.getConfigurator();
                 List<String> connProp = c.getDBMSConnectionParameter();
                 conn = DriverManager.getConnection(connProp.get(0), connProp.get(1), connProp.get(2));
            }
            catch (SQLException e){
                System.out.println("Connection failed!");
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }

            instance  = new SessionManager();
        }
        return instance;
    }

    public Connection getConnection(){
        return conn;
    }

}
