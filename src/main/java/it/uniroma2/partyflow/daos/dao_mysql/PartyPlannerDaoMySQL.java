package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceConstraintViolationException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing party planner persistence.
 *
 * This class provides operations to store party planner data in the
 * database using JDBC.
 *
 */

public class PartyPlannerDaoMySQL {

    public void insertPartyPlanner(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException {

        try{
            ConnectionFactory connFactory = ConnectionFactory.getInstance();
            Connection conn = connFactory.getConnection();

            // insert the new account in the 'PartyPlanner' table
            String query = "insert into partyplanner (email) value (?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getEmail());

            stmt.executeUpdate();
        }

        catch (PersistenceServiceUnavailableException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            switch (e.getErrorCode()){
                case 1062:
                    // Primary key o UNIQUE duplicated
                    throw new DuplicateEntityException();

                case 1451, 1452:
                    // Break Foreign Key constraint
                    throw new PersistenceConstraintViolationException();

                default:
                    throw new PersistenceGenericException();
            }
        }


    }
}
