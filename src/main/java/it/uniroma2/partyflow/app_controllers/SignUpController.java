package it.uniroma2.partyflow.app_controllers;

import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.daos.UserDaoInterface;
import it.uniroma2.partyflow.daos.dao_factories.DaoAbstractFactory;
import it.uniroma2.partyflow.exceptions.uiexception.PersistenceError;
import it.uniroma2.partyflow.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SignUpController {

    public void createAccount(UserBean signUpBean) throws PersistenceError{

        // --------convert the bean to the model---------

        // ------------- converts the date --------------
        // defines the pattern
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/uuuu");

        LocalDate date =
                LocalDate.parse(signUpBean.getDateOfBirth(), formatter);
        // -----------------------------------------------

        User user = new User(signUpBean.getName(), signUpBean.getSurname(),
                signUpBean.getEmail(), signUpBean.getPassword(),
                date, signUpBean.getAccountType(),
                signUpBean.getGender());

        // ------------------------------------------------


        // pass the model to the persistence layer
        DaoAbstractFactory factory = DaoAbstractFactory.getFactory();
        UserDaoInterface signUpDao = factory.getUserDao();
        signUpDao.signUp(user);


    }
}
