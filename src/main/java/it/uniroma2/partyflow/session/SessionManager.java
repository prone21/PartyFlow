package it.uniroma2.partyflow.session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance = null;
    private static final String URL = "jdbc:mysql://localhost:3306/partyflow";
    private static final String USER = "root";
    private static final String PWD = "root";
    private static Connection conn;

    private SessionManager(){};

    public static SessionManager getSessionManager() throws SQLException {
        if (instance == null){
            try{
                 conn = DriverManager.getConnection(URL, USER, PWD);
            }
            catch (SQLException e){
                System.out.println("Connection failed!");
                e.printStackTrace();
            }

            instance  = new SessionManager();
        }
        return instance;
    }

}
