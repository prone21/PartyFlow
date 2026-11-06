package it.uniroma2.partyflow.daos.dao_factories;


import it.uniroma2.partyflow.daos.dao_csv.UserDaoCSV;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;

public class DaoCSVFactory extends DaoAbstractFactory {
    private static DaoCSVFactory instance;

    private DaoCSVFactory(){}

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
