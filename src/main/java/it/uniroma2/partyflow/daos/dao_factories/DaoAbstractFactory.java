package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.IOException;

public abstract class DaoAbstractFactory {

    public static DaoAbstractFactory getFactory() throws PersistenceGenericException {
        try {
            PersistenceConfigurator persconf = PersistenceConfigurator.getInstance();
            return switch (persconf.getPersistenceType()) {
                case PersistenceType.MySQL -> DaoMySQLFactory.getInstance();
                case PersistenceType.CSV -> DaoCSVFactory.getInstance();
                case PersistenceType.InMemory -> DaoInMemoryFactory.getInstance();
            };
        }

        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }
    }


    public abstract UserDaoInterface getUserDao();


}
