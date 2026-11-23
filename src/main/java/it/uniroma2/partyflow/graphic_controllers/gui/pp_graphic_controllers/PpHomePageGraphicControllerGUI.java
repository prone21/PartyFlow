package it.uniroma2.partyflow.graphic_controllers.gui.pp_graphic_controllers;

import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.utilities.navigators.GUINavigatorSingleton;
import javafx.fxml.FXML;

/**
 * Graphic controller responsible for managing the Party Planner home page.
 *
 * <p>Handles user interactions related to event creation and management,
 * delegating navigation between views to GUINavigatorSingleton.</p>
 *
 * <p>For features that are not yet implemented, informs the user
 * through HandlerJavaFxGUIError.</p>
 */

public class PpHomePageGraphicControllerGUI {

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


    /**
     * Handles the user's request to create a new hangout.
     *
     * <p>Currently displays an informational message indicating
     * that the feature has not been implemented yet.</p>
     */
    @FXML
    public void createHangout() {
        HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();
        handlerJavaFxGUIError.infoMessage("'Create Hangout' feature is not implemented yet.");
    }

    /**
     * Navigates to the My Events page, from which the Party Planner
     * can access their created events.
     *
     * <p>If navigation fails, the resulting exception is logged
     * and handled as a fatal error by HandlerSystemError.</p>
     */
    @FXML
    public void myEvents(){
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goToView("/it/uniroma2/partyflow/view/Party_Planner/myEvents.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to navigate to the 'My Events' page: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
        }
    }

    /**
     * Handles the user's request to create a new public event.
     *
     * <p>Currently displays an informational message indicating
     * that the feature has not been implemented yet.</p>
     */
    @FXML
    public void createPublicEvent(){
        HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();
        handlerJavaFxGUIError.infoMessage("'Create public event' feature is not implemented yet.");
    }

}
