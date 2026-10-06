package it.uniroma2.partyflow.dao.signupdao;

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

    public SignUpDaoInterface getDao(){
        if (persistenceLayerType == "DBMS"){
            return new SignUpDaoDBMS();
        }
        else if (persistenceLayerType == "CSV"){
            return new SignUpDaoCSV();
        }
        else{
            return null;
        }
    }

}
