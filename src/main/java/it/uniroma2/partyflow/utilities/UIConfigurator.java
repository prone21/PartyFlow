package it.uniroma2.partyflow.utilities;


import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UIConfigurator {
    // Its responsibility is to obtain the UI
    private final Properties properties = new Properties();
    private static UIConfigurator instance = null;


    private UIConfigurator(){
        try{
            FileInputStream input =
                    new FileInputStream("src/main/resources/it/uniroma2/partyflow/configUI.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerErrorFactory hef = HandlerErrorFactory.getInstance();
            HandlerSystemError hse = hef.getHandlerSystemError();
            hse.severeLog("IOException", e);

        }

    }

    // Guarantee Singleton
    public static UIConfigurator getInstance(){
        if (instance == null){
            instance = new UIConfigurator();
        }

        return instance;
    }


    public UIType getUI(){
        return UIType.valueOf(this.properties.getProperty("interface.type"));
    }


}