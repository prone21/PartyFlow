package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public abstract class DaoAbstractFactory {


    public static DaoAbstractFactory getFactory(PersistenceType percistenceType) {


            switch (percistenceType){
                case DBMS -> {return DaoFactoryDBMS.getInstance();}
                case CSV -> {return DaoFactoryCSV.getInstance();}
                case DEMO -> {return DaoFactoryDEMO.getInstance();}
                default -> {return null;}
            }
    }

    public abstract SignUpDaoInterface getSignUpDao(User cred);
    public abstract LoginDaoInterface getLoginDao(LoginCredential cred);


}
