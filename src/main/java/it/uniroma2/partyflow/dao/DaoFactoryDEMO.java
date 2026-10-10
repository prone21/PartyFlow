package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.logindao.LoginDaoNoPersistence;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoNoPersistence;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoFactoryDEMO extends DaoAbstractFactory{
    private static DaoFactoryDEMO instance;

    private DaoFactoryDEMO(){}

    public static DaoFactoryDEMO getInstance(){
        if (instance == null){
            instance = new DaoFactoryDEMO();
        }

        return instance;
    }

    public SignUpDaoInterface getSignUpDao(User cred){
        return new SignUpDaoNoPersistence(cred);
    }

    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoNoPersistence(cred);
    }
}
