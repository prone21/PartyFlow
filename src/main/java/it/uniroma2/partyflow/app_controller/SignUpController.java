package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.CredentialsBean;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoFactory;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoDBMS;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.utilities.Configurator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SignUpController {

    public void createAccount(CredentialsBean signUpBean) throws SQLException, NoSuchAlgorithmException {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/uuuu");

        LocalDate date =
                LocalDate.parse(signUpBean.getDateOfBirth(), formatter);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes =
                digest.digest(signUpBean.getPassword().getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        String hashedPassword = sb.toString();


        User user = new User(signUpBean.getName(), signUpBean.getSurname(),
                signUpBean.getEmail(), hashedPassword,
                date, signUpBean.getAccountType(),
                signUpBean.getGender());

        try{
            Configurator c = Configurator.getConfigurator();
            SignUpDaoFactory signUpDao = SignUpDaoFactory.getInstance(c.getPersistenceLayer());
            SignUpDaoInterface dao = signUpDao.getDao();

            SignUpDaoDBMS signUpDAO = new SignUpDaoDBMS(user);
            signUpDAO.createAccount();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
