package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.dao.LoginDaoDBMS;
import it.uniroma2.partyflow.model.LoginCredential;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginappController {
    public void login(LoginCredentialsBean cred) throws SQLException, NoSuchAlgorithmException {
        LoginCredential usCred = new LoginCredential(cred.getEmail(),cred.getPassword());

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes =
                digest.digest(cred.getPassword().getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        String hashedPassword = sb.toString();

        usCred.setPassword(hashedPassword);

        LoginDaoDBMS loginDao = new LoginDaoDBMS(usCred);
        switch(loginDao.checkCredentials()){
            case Participant:
                System.out.println("è un partecipante");
                break;

            case PartyPlanner:
                System.out.println("è un partyplanner");
                break;

            default:
                System.out.println("non esiste sto account");
                break;
        }

    }
}
