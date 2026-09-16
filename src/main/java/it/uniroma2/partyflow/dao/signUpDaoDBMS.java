package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.session.SessionManager;

import java.sql.*;
import java.time.LocalDate;

public class signUpDaoDBMS {
    private User user;
    public signUpDaoDBMS(User user){
        this.user = user;
    }

    public void createAccount() throws SQLException {
        SessionManager sm = SessionManager.getSessionManager();
        Connection conn = sm.getConnection();

        String table;

        if (user.getAccountType() == AccountType.Participant) {
            table = "participant";
        } else if (user.getAccountType() == AccountType.PartyPlanner) {
            table = "partyplanner";
        } else {
            throw new SQLException("Invalid account type");
        }

        String query =
                "INSERT INTO " + table +
                        " (email, name, surname, password, dateOfBirth, gender) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(query);

        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getSurname());
        stmt.setString(4, user.getPwd());

        stmt.setDate(
                5,
                Date.valueOf(user.getDateOfBirth())
        );

        if (user.getGender() == null) {
            stmt.setNull(6, Types.VARCHAR);
        } else {
            stmt.setString(6, user.getGender().name());
        }

        stmt.executeUpdate();
    }
}
