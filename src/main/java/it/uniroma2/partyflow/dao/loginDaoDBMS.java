package it.uniroma2.partyflow.dao;
import it.uniroma2.partyflow.session.SessionManager;
import it.uniroma2.partyflow.beans.loginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;

import java.sql.SQLException;

public class loginDaoDBMS {
    LoginCredential cred;

    public loginDaoDBMS(LoginCredential cred){
        this.cred = cred;
    }

    public AccountType checkCredentials() throws SQLException {
        SessionManager.getSessionManager();
        return null;
    }
}
