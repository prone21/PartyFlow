package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class QueryMySQLManager {

    private QueryMySQLManager() {}

    public static void persistAccountType(String email, AccountType at) throws PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException, DuplicateEntityException, PersistenceConstraintViolationException, PersistenceGenericException {
        PreparedStatement stmt = null;

        try{
            ConnectionFactory connFactory = ConnectionFactory.getInstance();
            Connection conn = connFactory.getConnection();
            String query = null;
            switch (at){
                case Participant -> query = "insert into participant (email) value (?)";
                case PartyPlanner -> query = "insert into partyplanner (email) value (?)";
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
}
