package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.daos.dao_mysql.UserDaoMySQL;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

public class DaoMySQLFactory extends DaoAbstractFactory {
    private static DaoMySQLFactory instance;

    private DaoMySQLFactory(){
        HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
        handlerSystemError.logInfo("Using MySQL persistence as specified in configPersistency.ini");
    }

    public static DaoMySQLFactory getInstance(){
        if(instance == null){
            instance = new DaoMySQLFactory();
        }
        return instance;
    }

    @Override
    public UserDaoInterface getUserDao(){
        return new UserDaoMySQL();
    }

}
