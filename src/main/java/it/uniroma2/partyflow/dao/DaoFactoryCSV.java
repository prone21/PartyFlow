package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoCSV;
import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoCSV;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoFactoryCSV extends DaoAbstractFactory{
    private static DaoFactoryCSV instance;

    private DaoFactoryCSV(){}

    public static DaoFactoryCSV getInstance(){
        if(instance == null){
            instance = new DaoFactoryCSV();
        }

        return instance;
    }

    public SignUpDaoInterface getSignUpDao(User cred){
        return new SignUpDaoCSV(cred);
    }

    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoCSV(cred);
    }
}
