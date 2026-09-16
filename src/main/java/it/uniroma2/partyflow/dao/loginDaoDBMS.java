package it.uniroma2.partyflow.dao;
import java.sql.Statement;
import java.sql.ResultSet;
import it.uniroma2.partyflow.session.SessionManager;
import it.uniroma2.partyflow.beans.loginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;

import java.sql.Connection;
import java.sql.SQLException;

public class loginDaoDBMS {
    LoginCredential cred;

    public loginDaoDBMS(LoginCredential cred){
        this.cred = cred;
    }

    public AccountType checkCredentials() throws SQLException {
        SessionManager sm = SessionManager.getSessionManager();
        Connection conn = sm.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(    "select email from participant where email='"+cred.getEmail()+"' and password='"+cred.getPassword()+"'");
        if (rs.next()) {
            return AccountType.Participant;
        } else {
            rs = stmt.executeQuery("select email from partyplanner where email='"+cred.getEmail()+"' and password='"+cred.getPassword()+"'");
            if (rs.next()) {
                return AccountType.PartyPlanner;
            } else {
                System.out.println("non ci sta sto account");
                return AccountType.NULLO;
            }
        }
    }



}
