package it.uniroma2.partyflow.utilities.configurators;

import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Its responsibility is to obtain the application UI type
 */

public class UIConfigurator {
    private final Properties properties = new Properties();
    private static UIConfigurator instance = null;


    private UIConfigurator(){
        try{
            FileInputStream input =
                    new FileInputStream("src/main/resources/it/uniroma2/partyflow/configurations/configUI.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerSystemError handlerSysErr = HandlerSystemError.getInstance();
            handlerSysErr.logError(e);

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