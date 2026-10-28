package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.logindao.LoginDaoCSV;
import it.uniroma2.partyflow.daos.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.daos.signupdao.SignUpDaoCSV;
import it.uniroma2.partyflow.daos.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoCSVFactory extends DaoAbstractFactory {
    private static DaoCSVFactory instance;

    private DaoCSVFactory(){}

    public static DaoCSVFactory getInstance(){
        if(instance == null){
            instance = new DaoCSVFactory();
        }

        return instance;
    }

    @Override
    public SignUpDaoInterface getSignUpDao(User accountInfo){
        return new SignUpDaoCSV(accountInfo);
    }

    @Override
    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoCSV(cred);
    }
}
