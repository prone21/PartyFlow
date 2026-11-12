package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.exceptions.daoexception.InvalidPersistenceTypeException;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;

import java.sql.*;

public class UserDaoMySQL implements UserDaoInterface {

    private Connection conn = null;

    private void connect() throws PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException {

            ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
            this.conn = connectionFactory.getConnection();
    }

    // rollback all the action done
    private void rollBack() throws PersistenceGenericException {
        try{
            if(this.conn != null){
                this.conn.rollback();
            }
        }
        catch (SQLException e1){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e1);
        }
    }

    @Override
    public void signUp(User user) throws PersistenceServiceUnavailableException, DuplicateEntityException,
            PersistenceGenericException, PersistenceConstraintViolationException, InvalidPersistenceTypeException {

        PreparedStatement stmt = null;

        try{
            this.connect();
            this.conn.setAutoCommit(false); // start transaction

            // insert the new account in the 'users' table
            String query = "insert into users (email, name, surname, password, dateOfBirth" +
                    ", gender) value (?,?,?,?,?,?)";

            stmt = this.conn.prepareStatement(query);

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

            this.conn.commit(); // end transaction
        }

        catch (InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        catch (PersistenceServiceUnavailableException | DuplicateEntityException |
               PersistenceConstraintViolationException | PersistenceGenericException e){

            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack();

            // throw the exception
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack();

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
            try{
                if (this.conn != null){
                    this.conn.setAutoCommit(true);
                }
            }
            catch (SQLException e1){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e1);
            }

            try {
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
    public AccountType login(LoginCredential cred) throws PersistenceGenericException,
            EntityNotFound, PersistenceServiceUnavailableException, InvalidPersistenceTypeException {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            this.connect();
            String password = cred.getPassword();

            // check if the user is a participant or a party planner
            String query = "select u.password as password, COALESCE(p.email,'PartyPlanner') as account_type\n" +
                    "from users as u left join participant as p on u.email = p.email " +
                    "where u.email = ?";

            stmt = this.conn.prepareStatement(query);
            stmt.setString(1,cred.getEmail());

            rs = stmt.executeQuery();

            if (rs.next() && PasswordProtector.checkPassword(password, rs.getString("password"))){
                if (rs.getString("account_type").equals("PartyPlanner")){
                    return AccountType.PartyPlanner;
                }
                else{
                    return AccountType.Participant;
                }

            }
            else{
                throw new EntityNotFound();
            }
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }

        catch (PersistenceServiceUnavailableException | EntityNotFound | InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        finally{
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
