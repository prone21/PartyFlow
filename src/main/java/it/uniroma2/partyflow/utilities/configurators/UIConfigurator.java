package it.uniroma2.partyflow.utilities.configurators;

import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Singleton responsible for managing the application UI configuration.
 *
 * <p>Loads UI properties from a configuration file and determines
 * which user interface type (CLI or JavaFX GUI) must be used.</p>
 */


public class UIConfigurator {
    private final Properties properties = new Properties();
    private static UIConfigurator instance = null;


    private UIConfigurator() throws IOException{
        try (FileInputStream input = new FileInputStream("configurations/configUI.ini")){
            this.properties.load(input); // it puts the content of the .ini file into the properties var
        }
        catch (IOException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw e;
        }
    }

    // Ensures a single instance
    public static UIConfigurator getInstance() throws IOException {
        if (instance == null){
            instance = new UIConfigurator();
        }
        return instance;

    }


    /**
     * Retrieves the user interface type specified in the configuration file.
     *
     * @return the configured UI type
     * @throws IllegalArgumentException thrown by UIType.valueOf()
     * if the configured UI type does not match any enum constant
     * @throws NullPointerException thrown by UIType.valueOf()
     * if the 'interface.type' property is missing
     */

    public UIType getUI(){
        return UIType.valueOf(this.properties.getProperty("interface.type"));
    }


}