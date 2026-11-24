package it.uniroma2.partyflow.utilities;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class responsible for executing MySQL operations
 * shared by multiple dao_mysql.
 */

public class QueryMySQLManager {

    private QueryMySQLManager() {}


    /**
     * Persists the user's account type by inserting their email
     * into the corresponding MySQL table (participant or partyplanner).
     *
     * <p>Uses a prepared statement to execute the SQL query, logs
     * persistence-related errors, and translates SQL exceptions into
     * application-specific persistence exceptions.</p>
     *
     * @param email the email identifying the user
     * @param at the account type to persist
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws InvalidPersistenceTypeException if the configured persistence type is invalid
     * @throws DuplicateEntityException if the record already exists
     * @throws PersistenceConstraintViolationException if a foreign key constraint is violated
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     */

    public static void persistAccountType(String email, AccountType at) throws PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException, DuplicateEntityException, PersistenceConstraintViolationException, PersistenceGenericException {
        PreparedStatement stmt = null;

        try{
            Connection conn = ConnectionFactory.getConnection();
            String query = null;
            switch (at){
                case PARTICIPANT -> query = "insert into participant (email) value (?)";
                case PARTY_PLANNER -> query = "insert into partyplanner (email) value (?)";
            }
            stmt = conn.prepareStatement(query);
            stmt.setString(1,email);
            stmt.executeUpdate();
        }
        catch (PersistenceServiceUnavailableException | InvalidPersistenceTypeException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            switch (e.getErrorCode()){
                case 1062:
                    // Primary key or UNIQUE duplicated
                    throw new DuplicateEntityException();

                case 1451, 1452:
                    // Break Foreign Key constraint
                    throw new PersistenceConstraintViolationException();

                default:
                    throw new PersistenceGenericException();
            }
        }
        finally {
            try {
                if(stmt != null){
                    stmt.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }
    }

    /**
     * Checks whether a participant/party planner with the specified email exists
     * in the MySQL database.
     *
     * @param email the email address identifying the participant/party planner
     * @return true if the participant/party planner exists, false otherwise
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */
    public static boolean exists(String email, AccountType at) throws PersistenceServiceUnavailableException, InvalidPersistenceTypeException, PersistenceGenericException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // connect to db
            Connection conn = ConnectionFactory.getConnection();

            // Checks whether a party planner/participant with the specified email exists.
            // Returns 1 for each matching record, without retrieving user data.
            String query = null;
            switch (at){
                case PARTY_PLANNER -> query = "select 1 from partyplanner where email = ?";
                case PARTICIPANT -> query = "select 1 from participant where email = ?";
            }

            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            return rs.next();
        } catch (PersistenceServiceUnavailableException | InvalidPersistenceTypeException e) {
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        } catch (SQLException e) {
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e2) {
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }
    }



}
