package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.dao.LoginDaoDBMS;
import it.uniroma2.partyflow.model.LoginCredential;

import java.sql.SQLException;

public class LoginappController {
    public void login(LoginCredentialsBean cred) throws SQLException {
        LoginCredential usCred = new LoginCredential(cred.getEmail(),cred.getPassword());

        LoginDaoDBMS loginDao = new LoginDaoDBMS(usCred);
        switch(loginDao.checkCredentials()){
            case Participant:
                System.out.println("è un partecipante");
                break;

            case PartyPlanner:
                System.out.println("è un partyplanner");
                break;

            default:
                break;
        }

    }
}
