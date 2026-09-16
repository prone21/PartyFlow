package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.loginCredentialsBean;
import it.uniroma2.partyflow.dao.loginDaoDBMS;
import it.uniroma2.partyflow.model.LoginCredential;

import java.sql.SQLException;

import static it.uniroma2.partyflow.enums.AccountType.Participant;
import static it.uniroma2.partyflow.enums.AccountType.PartyPlanner;

public class loginappController {
    public void login(loginCredentialsBean cred) throws SQLException {
        LoginCredential usCred = new LoginCredential(cred.getEmail(),cred.getPassword());

        loginDaoDBMS loginDao = new loginDaoDBMS(usCred);
        switch(loginDao.checkCredentials()){
            case Participant:
                System.out.println("è un partecipante");
                break;

            case PartyPlanner:
                System.out.println("è un partplanner");
                break;

            default:
                break;
        }

    }
}
