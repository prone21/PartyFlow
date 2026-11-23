package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.QueryMySQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing participant persistence.
 */


public class ParticipantDaoMySQL {
    private Connection conn = null;

    /**
     * Persists a user as a participant by delegating the insertion
     * of their email into the participant table to QueryMySQLManager.
     *
     * @param user the user to persist as a participant
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws PersistenceConstraintViolationException if a database constraint is violated
     * @throws DuplicateEntityException if the participant already exists
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */

    public void insertParticipant(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException, InvalidPersistenceTypeException {

        QueryMySQLManager.persistAccountType(user.getEmail(), AccountType.PARTICIPANT);

    }

    /**
     * Checks whether a participant with the specified email exists
     * in the MySQL database.
     *
     * @param email the email address identifying the participant
     * @return true if the participant exists, false otherwise
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */
    public boolean existsParticipant(String email) throws PersistenceGenericException, PersistenceServiceUnavailableException, InvalidPersistenceTypeException {
        PreparedStatement  stmt = null;
        ResultSet rs = null;

        try{
            // connect to db
            this.conn = ConnectionFactory.getConnection();

            // Checks whether a participant with the specified email exists.
            // Returns 1 for each matching record, without retrieving user data.
            String query = "select 1 from participant where email = ?";
            stmt = this.conn.prepareStatement(query);
            stmt.setString(1,email);
            rs = stmt.executeQuery();

            return rs.next();
        }
        catch(PersistenceServiceUnavailableException | InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if(rs != null){
                    rs.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }

    }
}
