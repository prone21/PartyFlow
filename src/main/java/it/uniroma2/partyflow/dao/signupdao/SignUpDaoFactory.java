package it.uniroma2.partyflow.dao.signupdao;

import it.uniroma2.partyflow.model.User;

public class SignUpDaoFactory {
    static private SignUpDaoFactory instance;
    String persistenceLayerType;
    private SignUpDaoFactory(String persistenceLayerType){
        this.persistenceLayerType = persistenceLayerType;
    }

    public static SignUpDaoFactory getInstance(String persistenceLayerType){
        if(instance == null){
            instance = new SignUpDaoFactory(persistenceLayerType);
        }
        return instance;
    }

    public SignUpDaoInterface getDao(User user){
        if (persistenceLayerType.equals("DBMS")){
            return new SignUpDaoDBMS(user);
        }
        else if (persistenceLayerType.equals("CSV")){
            return new SignUpDaoCSV(user);
        }
        else{
            return null;
        }
    }

}
