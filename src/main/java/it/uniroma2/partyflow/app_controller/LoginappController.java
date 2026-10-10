package it.uniroma2.partyflow.app_controller;

import com.mysql.cj.log.Log;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.dao.DaoAbstractFactory;
import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
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



        try{
            Configurator c = Configurator.getConfigurator();
            DaoAbstractFactory factory = DaoAbstractFactory.getFactory(c.getPersistenceLayer());
            LoginDaoInterface loginDao = factory.getLoginDao(usCred);
            return loginDao.login();
        }
        catch (IOException e){
            e.printStackTrace();
            return  null;
        }




    }
}
