package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public abstract class DaoAbstractFactory {

    private static PersistenceType persistenceType;

    public static DaoAbstractFactory getFactory() {
            return switch (persistenceType){
                case PersistenceType.MySQL -> DaoFactoryDBMS.getInstance();
                case PersistenceType.CSV -> DaoFactoryCSV.getInstance();
                case PersistenceType.InMemory -> DaoFactoryDEMO.getInstance();
            };
    }

    public static void setPersistenceType(PersistenceType pt){
        persistenceType = pt;
    }

    public abstract SignUpDaoInterface getSignUpDao(User cred);
    public abstract LoginDaoInterface getLoginDao(LoginCredential cred);


}
