package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.utilities.configurators.PersistenceConfigurator;

import java.io.IOException;


/**
 * Abstract factory responsible for providing DAO objects
 * independently of the underlying persistence technology.
 *
 * <p>Implements the GoF Abstract Factory pattern by defining
 * a common contract for concrete DAO factories.
 * Selects the appropriate concrete factory (MySQL, CSV, or In-Memory)
 * according to the persistence type provided by PersistenceConfigurator.</p>
 */


public abstract class DaoAbstractFactory {

    public static DaoAbstractFactory getFactory() throws PersistenceGenericException {
        try {
            PersistenceConfigurator persconf = PersistenceConfigurator.getInstance();
            return switch (persconf.getPersistenceType()) {
                case PersistenceType.MY_SQL -> DaoMySQLFactory.getInstance();
                case PersistenceType.CSV -> DaoCSVFactory.getInstance();
                case PersistenceType.IN_MEMORY -> DaoInMemoryFactory.getInstance();
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
