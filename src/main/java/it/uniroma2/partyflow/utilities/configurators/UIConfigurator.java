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


    private UIConfigurator() throws IOException{
        try {
            FileInputStream input =
                    new FileInputStream("configurations/configUI.ini");
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }
    }

    // Guarantee Singleton
    public static UIConfigurator getInstance() throws IOException {
        if (instance == null){
            instance = new UIConfigurator();
        }
        return instance;

    }


    public UIType getUI(){
        return UIType.valueOf(this.properties.getProperty("interface.type"));
    }


}