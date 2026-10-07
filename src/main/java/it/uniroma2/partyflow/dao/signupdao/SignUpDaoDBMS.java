package it.uniroma2.partyflow.dao.signupdao;

import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.session.SessionManager;

import java.sql.*;

public class SignUpDaoDBMS implements SignUpDaoInterface {
    User cred;

    public void setCredentials(User user){
        this.cred = user;
    }

    public SignUpDaoDBMS(User cred){
        this.cred = cred;
    }

    public void createAccount() {
        try {
            SessionManager sm = SessionManager.getSessionManager();
            Connection conn = sm.getConnection();
            String query = "insert into users (email, name, surname, password, dateOfBirth" +
                    ", gender, accountType) value (?,?,?,?,?,?,?)";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, cred.getEmail());
            stmt.setString(2, cred.getName());
            stmt.setString(3,cred.getSurname());
            stmt.setString(4, cred.getPwd());
            stmt.setDate(5, Date.valueOf(cred.getDateOfBirth()));
            stmt.setString(6, cred.getGender().name());
            stmt.setString(7,cred.getAccountType().name());

            stmt.executeUpdate();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
