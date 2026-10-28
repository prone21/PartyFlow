package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.daos.logindao.LoginDaoInMemory;
import it.uniroma2.partyflow.daos.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.daos.signupdao.SignUpDaoInMemory;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoInMemoryFactory extends DaoAbstractFactory {
    private static DaoInMemoryFactory instance;

    private DaoInMemoryFactory(){}

    public static DaoInMemoryFactory getInstance(){
        if (instance == null){
            instance = new DaoInMemoryFactory();
        }

        return instance;
    }

    public SignUpDaoInterface getSignUpDao(User accountInfo){
        return new SignUpDaoInMemory(accountInfo);
    }

    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoInMemory(cred);
    }
}
