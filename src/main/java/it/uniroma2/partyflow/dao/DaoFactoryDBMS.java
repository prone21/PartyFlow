package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoDBMS;
import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoDBMS;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoFactoryDBMS extends DaoAbstractFactory {
    private static DaoFactoryDBMS instance;

    private DaoFactoryDBMS(){};

    public static DaoFactoryDBMS getInstance(){
        if(instance == null){
            instance = new DaoFactoryDBMS();
        }
        return instance;
    }

    public SignUpDaoInterface getSignUpDao(User cred){
        return new SignUpDaoDBMS(cred);
    }

    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoDBMS(cred);
    }

}
