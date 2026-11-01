package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationConfigurationException;
import it.uniroma2.partyflow.exceptions.uiexception.PersistenceError;
import it.uniroma2.partyflow.exceptions.uiexception.ServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.uiexception.UserOperationException;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoMySQL implements UserDaoInterface{

    @Override
    public void signUp(User user) throws PersistenceError {

        ConnectionFactory connFactory = ConnectionFactory.getInstance();
        Connection conn = connFactory.getConnection();
        PreparedStatement stmt = null;

        try{

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
        catch (SQLException | UserOperationException e){
            try{
                conn.rollback();
                throw new PersistenceError();
            }
            catch (SQLException e1){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e1);
            }

        }

        finally{
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
    public AccountType login(LoginCredential cred){
        return null;
    }

}
