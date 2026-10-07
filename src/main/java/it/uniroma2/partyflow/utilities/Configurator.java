package it.uniroma2.partyflow.utilities;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configurator {
    private final Properties properties = new Properties();
    private static Configurator confInstance = null;

    private Configurator() throws IOException {
        FileInputStream input =
                new FileInputStream("src/main/resources/it/uniroma2/partyflow/config.ini");

        properties.load(input);
    }

    public static Configurator getConfigurator() throws IOException {
        if (confInstance == null){
            confInstance = new Configurator();
        }

        return confInstance;
    }

    public String getPersistenceLayer(){
        return this.properties.getProperty("persistence") ;
    }

    public String getUI(){
        return this.properties.getProperty("interface");
    }

    public List<String> getDBMSConnectionParameter(){
        if(this.getPersistenceLayer().equals("DBMS")) {
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
        if (this.getPersistenceLayer().equals("CSV")){
            return this.properties.getProperty("directoryCSVDB");
        }
        else {
            return null;
        }
    }

}