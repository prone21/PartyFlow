package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.dao_inmemory.UserDaoInMemory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;


/**
 * Concrete factory responsible for creating InMemory DAO objects.
 *
 * <p>Extends DaoAbstractFactory according to the GoF Abstract Factory
 * pattern and implements the Singleton pattern to provide a single
 * factory instance.</p>
 */


public class DaoInMemoryFactory extends DaoAbstractFactory {
    private static DaoInMemoryFactory instance;

    private DaoInMemoryFactory(){
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logInfo("Using IN_MEMORY persistence as specified in configPersistency.ini");
    }

    public static DaoInMemoryFactory getInstance(){
        if (instance == null){
            instance = new DaoInMemoryFactory();
        }

        return instance;
    }

    @Override
    public UserDaoInterface getUserDao(){
        return new UserDaoInMemory();
    }

}
