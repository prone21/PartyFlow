package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceConstraintViolationException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

import java.sql.*;

public class UserDaoMySQL implements UserDaoInterface {

    // rollback all the action done
    private void rollBack(Connection conn){
        try{
            if(conn != null){
                conn.rollback();
            }
        }
        catch (SQLException e1){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e1);
        }
    }

    @Override
    public void signUp(User user) throws PersistenceServiceUnavailableException, DuplicateEntityException, PersistenceGenericException, PersistenceConstraintViolationException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            ConnectionFactory connFactory = ConnectionFactory.getInstance();
            conn = connFactory.getConnection();
            conn.setAutoCommit(false);

            // insert the new account in the 'users' table
            String query = "insert into users (email, name, surname, password, dateOfBirth" +
                    ", gender) value (?,?,?,?,?,?)";

            stmt = conn.prepareStatement(query);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getPwd());
            stmt.setDate(5, Date.valueOf(user.getDateOfBirth()));
            stmt.setString(6, user.getGender().name());

            stmt.executeUpdate();

            // Insert the user into the table corresponding to their account type.
            switch (user.getAccountType()) {
                case PartyPlanner -> {
                    PartyPlannerDaoMySQL plannerDaoMySQL = new PartyPlannerDaoMySQL();
                    plannerDaoMySQL.insertPartyPlanner(user);
                }
                case Participant -> {
                    ParticipantDaoMySQL participantDaoMySQL = new ParticipantDaoMySQL();
                    participantDaoMySQL.insertParticipant(user);
                }
            }

            conn.commit();
        }

        catch (PersistenceServiceUnavailableException | DuplicateEntityException |
               PersistenceConstraintViolationException | PersistenceGenericException e){

            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack(conn);

            // throw the exception
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
                    // rollback all the action done
                    this.rollBack(conn);
                    throw new PersistenceGenericException();
            }
        }

        finally {
            try {
                if (conn != null){
                    conn.setAutoCommit(true);
                }

                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }
    }


    @Override
    public AccountType login(LoginCredential cred) throws PersistenceServiceUnavailableException, DuplicateEntityException, PersistenceConstraintViolationException, PersistenceGenericException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            ConnectionFactory connFactory = ConnectionFactory.getInstance();
            conn = connFactory.getConnection();
            conn.setAutoCommit(false);


            // check if he is a participant
            String query = "select * from users as u join participant as p on p.email = u.email " +
                    "where u.email = ? and u.password = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1,cred.getEmail());
            stmt.setString(2,cred.getPassword());

            rs = stmt.executeQuery();
            if (rs.next()){
                return AccountType.Participant;
            }
            else{
                return AccountType.PartyPlanner;
            }
        }

        catch (PersistenceServiceUnavailableException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack(conn);

            // throw the exception
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
                    // rollback all the action done
                    this.rollBack(conn);
                    throw new PersistenceGenericException();
            }
        }

        finally{
            try {
                if (conn != null){
                    conn.setAutoCommit(true);
                }

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
