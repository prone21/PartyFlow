package it.uniroma2.partyflow.graphic_controllers.gui.graphic_controllers_in_common;

import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.utilities.navigators.GUINavigatorSingleton;
import javafx.fxml.FXML;

public class HeaderGraphicControllerGUI {
    /**
     * Navigates back to the previous view through GUINavigatorSingleton.
     *
     * <p>If navigation fails, the resulting exception is logged
     * and handled as a fatal error by HandlerSystemError.</p>
     */

    @FXML
    public void goBackward() {
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goBackward();
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("GoBackward failed: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);

        }
    }
}
