package it.uniroma2.partyflow.daos.dao_factories;


import it.uniroma2.partyflow.daos.dao_csv.UserDaoCSV;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

/**
 * Concrete factory responsible for creating CSV DAO objects.
 *
 * <p>Extends DaoAbstractFactory according to the GoF Abstract Factory
 * pattern and implements the Singleton pattern to provide a single
 * factory instance.</p>
 */


public class DaoCSVFactory extends DaoAbstractFactory {
    private static DaoCSVFactory instance;

    private DaoCSVFactory(){
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logInfo("Using CSV persistence as specified in configPersistency.ini");
    }

    public static DaoCSVFactory getInstance(){
        if(instance == null){
            instance = new DaoCSVFactory();
        }

        return instance;
    }

    @Override
    public UserDaoInterface getUserDao(){
        return new UserDaoCSV();
    }
}
