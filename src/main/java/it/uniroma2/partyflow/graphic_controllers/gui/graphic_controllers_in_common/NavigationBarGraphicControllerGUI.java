package it.uniroma2.partyflow.graphic_controllers.gui.graphic_controllers_in_common;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.utilities.UserSession;
import it.uniroma2.partyflow.utilities.navigators.GUINavigatorSingleton;
import javafx.fxml.FXML;

/**
 * Graphic controller responsible for managing the navigation bar.
 *
 * <p>Handles navigation between the home page, notifications page
 * and profile page through GUINavigatorSingleton.</p>
 *
 * <p>Provides a common navigation bar for both Party Planners and
 * Participants, selecting the appropriate home page based on
 * the account type stored in the current user session.</p>
 */

public class NavigationBarGraphicControllerGUI {
    /**
     * Navigates to the appropriate home page based on the
     * account type of the authenticated user.
     *
     * <p>Retrieves the account type from the current UserSession
     * and delegates navigation to GUINavigatorSingleton.</p>
     *
     * <p>If session retrieval or navigation fails, the resulting
     * exception is logged and handled as a fatal error
     * by HandlerSystemError.</p>
     */
    @FXML
    public void goHomePage() {
        try{
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            UserSession session = UserSession.getInstance();

            if(session.getUserAccountType() == AccountType.PARTICIPANT){
                nav.goToView("/it/uniroma2/partyflow/view/Participant/participantHomePage.fxml");
            }
            else if(session.getUserAccountType() == AccountType.PARTY_PLANNER){
                nav.goToView("/it/uniroma2/partyflow/view/Party_Planner/partyPlannerHomePage.fxml");
            }
        }

        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to navigate to the home page: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
        }
    }

    /**
     * Navigates to the notifications page through GUINavigatorSingleton.
     *
     * <p>If navigation fails, the resulting exception is logged
     * and handled as a fatal error by HandlerSystemError.</p>
     */
    @FXML
    public void myNotifications(){
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goToView("/it/uniroma2/partyflow/view/view_in_common/myNotifications.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to navigate to the notifications page: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
        }
    }

    /**
     * Navigates to the user profile page through GUINavigatorSingleton.
     *
     * <p>If navigation fails, the resulting exception is logged
     * and handled as a fatal error by HandlerSystemError.</p>
     */
    @FXML
    public void goProfile(){
        try {
            GUINavigatorSingleton nav = GUINavigatorSingleton.getInstance();
            nav.goToView("/it/uniroma2/partyflow/view/view_in_common/profile.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to navigate to the profile page: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
        }
    }

}
