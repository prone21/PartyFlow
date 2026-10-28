package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.daos.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginappController {
    public AccountType login(LoginCredentialsBean cred) throws SQLException, NoSuchAlgorithmException {
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



        DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
        LoginDaoInterface loginDao = factory.getLoginDao(usCred);
        return loginDao.login();




    }
}
