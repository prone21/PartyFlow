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
        FileInputStream input =
                new FileInputStream("configurations/configUI.ini");
        this.properties.load(input); // it puts the content of the .ini file into the properties var
    }

    private UIConfigurator(Properties properties){
        // Copies all key-value pairs from the provided Properties object into this.properties
        this.properties.putAll(properties);
    }

    // Guarantee Singleton
    public static UIConfigurator getInstance() {
        try{
            if (instance == null){
                instance = new UIConfigurator();
            }
            return instance;
        }
        catch (IOException e){
            HandlerSystemError handlerSysErr = HandlerSystemError.getInstance();
            handlerSysErr.logWarning(
                    "Unable to load UI configuration from configurations/configUI.ini. "
                            + "Falling back to default UI configuration."
            );

            // ---------------return ui default configuration--------------------
            Properties defaultProperties = new Properties();
            defaultProperties.setProperty("interface.type", "JavaFxGUI");
            return new UIConfigurator(defaultProperties);
            // ------------------------------------------------------------------
        }

    }


    public UIType getUI(){
        return UIType.valueOf(this.properties.getProperty("interface.type"));
    }


}