package it.uniroma2.partyflow.app_controller;

import it.uniroma2.partyflow.beans.signUpCredentialsBean;
import it.uniroma2.partyflow.dao.signUpDaoDBMS;
import it.uniroma2.partyflow.model.User;

import java.sql.SQLException;

public class SignUpController {

    public void createAccount(signUpCredentialsBean signUpBean) throws SQLException {
        User user = new User(signUpBean.getName(), signUpBean.getSurname(),
                signUpBean.getEmail(), signUpBean.getPassword(),
                signUpBean.getDateOfBirth(), signUpBean.getAccountType(),
                signUpBean.getGender());

        signUpDaoDBMS signUpDAO = new signUpDaoDBMS(user);
        signUpDAO.createAccount();
    }
}
