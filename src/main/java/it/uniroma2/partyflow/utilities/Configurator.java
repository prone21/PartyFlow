package it.uniroma2.partyflow.utilities;


import it.uniroma2.partyflow.enums.PersistenceType;
import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configurator {
    private final Properties properties = new Properties();

    private static Configurator confInstance = null;


    private Configurator(){
        try{
            FileInputStream input =
                    new FileInputStream("src/main/resources/it/uniroma2/partyflow/config.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerErrorFactory hef = HandlerErrorFactory.getInstance();
            HandlerSystemError hse = hef.getHandlerSystemError();
            hse.severeLog("IOException", e);

        }

    }

    // Guarantee Singleton
    public static Configurator getConfigurator() throws IOException {
        if (confInstance == null){
            confInstance = new Configurator();
        }

        return confInstance;
    }



    public PersistenceType getPersistenceLayer(){
        return PersistenceType.valueOf(this.properties.getProperty("persistence")) ;
    }

    public UIType getUI(){
        return UIType.valueOf(this.properties.getProperty("interface"));
    }

    public List<String> getDBMSConnectionParameter(){
        if(this.getPersistenceLayer() == PersistenceType.DBMS) {
            List<String> DBMSConnproperties = new ArrayList<>();
            DBMSConnproperties.add(this.properties.getProperty("url"));
            DBMSConnproperties.add(this.properties.getProperty("username"));
            DBMSConnproperties.add(this.properties.getProperty("password"));
            DBMSConnproperties.add(this.properties.getProperty("url"));
            return DBMSConnproperties;
        }

        else{
            return null;
        }


    }

    public String getdirectoryCSVDB(){
        if (this.getPersistenceLayer() == PersistenceType.CSV){
            return this.properties.getProperty("directoryCSVDB");
        }
        else {
            return null;
        }
    }

}