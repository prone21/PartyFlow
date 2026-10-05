package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.CredentialsBean;
import it.uniroma2.partyflow.dao.SignUpDaoDBMS;
import it.uniroma2.partyflow.model.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SignUpController {

    public void createAccount(CredentialsBean signUpBean) throws SQLException {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/uuuu");

        LocalDate date =
                LocalDate.parse(signUpBean.getDateOfBirth(), formatter);


        User user = new User(signUpBean.getName(), signUpBean.getSurname(),
                signUpBean.getEmail(), signUpBean.getPassword(),
                date, signUpBean.getAccountType(),
                signUpBean.getGender());

        SignUpDaoDBMS signUpDAO = new SignUpDaoDBMS(user);
        signUpDAO.createAccount();
    }
}
