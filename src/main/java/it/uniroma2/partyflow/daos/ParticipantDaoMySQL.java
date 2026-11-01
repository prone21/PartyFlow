package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationConfigurationException;
import it.uniroma2.partyflow.exceptions.uiexception.PersistenceError;
import it.uniroma2.partyflow.exceptions.uiexception.ServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.uiexception.UserOperationException;
import it.uniroma2.partyflow.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//TODO scrivi il commento che spiega la classe

public class ParticipantDaoMySQL {
    public void insertParticipant(User user) throws UserOperationException, PersistenceError {
        try {
            ConnectionFactory connFactory = ConnectionFactory.getInstance();
            Connection conn = connFactory.getConnection();

            // insert the new account in the 'Participant' table
            String query = "insert into participant (email) value (?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getEmail());

            stmt.executeUpdate();
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("User registration failed - Participant",e);
            throw new UserOperationException();
        }

    }
}
