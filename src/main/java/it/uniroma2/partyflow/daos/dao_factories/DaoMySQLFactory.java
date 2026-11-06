package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.daos.dao_mysql.UserDaoMySQL;

public class DaoMySQLFactory extends DaoAbstractFactory {
    private static DaoMySQLFactory instance;

    private DaoMySQLFactory(){};

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
