package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.logindao.LoginDaoInterface;
import it.uniroma2.partyflow.daos.signupdao.SignUpDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

public abstract class DaoAbstractFactory {

    public static DaoAbstractFactory getFactory() {
        PersistenceConfigurator persconf = PersistenceConfigurator.getInstance();
            return switch (persconf.getPersistenceType()){
                case PersistenceType.MySQL -> DaoMySQLFactory.getInstance();
                case PersistenceType.CSV -> DaoCSVFactory.getInstance();
                case PersistenceType.InMemory -> DaoInMemoryFactory.getInstance();
            };
    }



    public abstract SignUpDaoInterface getSignUpDao(User accountInfo);
    public abstract LoginDaoInterface getLoginDao(LoginCredential cred);


}
