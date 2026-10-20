package it.uniroma2.partyflow.dao;

import it.uniroma2.partyflow.dao.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.dao.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

public abstract class DaoAbstractFactory {

    public static DaoAbstractFactory getFactory() {
        PersistenceConfigurator persconf = PersistenceConfigurator.getInstance();
            return switch (persconf.getPersistenceType()){
                case PersistenceType.MySQL -> DaoFactoryDBMS.getInstance();
                case PersistenceType.CSV -> DaoFactoryCSV.getInstance();
                case PersistenceType.InMemory -> DaoFactoryDEMO.getInstance();
            };
    }



    public abstract SignUpDaoInterface getSignUpDao(User cred);
    public abstract LoginDaoInterface getLoginDao(LoginCredential cred);


}
