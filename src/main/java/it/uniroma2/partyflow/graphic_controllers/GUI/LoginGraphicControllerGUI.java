package it.uniroma2.partyflow.graphic_controllers.GUI;

import it.uniroma2.partyflow.app_controllers.LoginController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class LoginGraphicControllerGUI {

     @FXML
     private TextField emailField;
     @FXML
     private TextField pwdField;


    @FXML
    public void goBackward() {
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.goBackward();
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("GoBackward failed: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);

        }
    }

    @FXML
    public void submitCredentials() {
        try{
            // 0) fetch user data
            String email = emailField.getText();
            String pwd = pwdField.getText();
            //---------------------------------------------

            // 1) create the bean where the user date will be memorized
            LoginCredentialsBean loginBean = new LoginCredentialsBean();
            loginBean.setEmail(email);
            loginBean.setPassword(pwd);
            //---------------------------------------------

            // 2) pass the LoginCredentialsBean to the application controller
            LoginController loginController = new LoginController();
            AccountType accountType = loginController.login(loginBean);
            //---------------------------------------------


            // 3) show the right homepage
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            switch(accountType){
                case Participant:
                    nav.gotoView("/it/uniroma2/partyflow/view/Participant/participantHomePage.fxml");
                    break;

                case PartyPlanner:
                    nav.gotoView("/it/uniroma2/partyflow/view/Party_Planner/partyPlannerHomePage.fxml");
                    break;
            }
            //---------------------------------------------
        }
        catch (RequiredFieldException e) {
            /*
             * Handles missing required fields in the sign-up form.
             * Uses a switch statement to identify the corresponding JavaFX element
             * and delegates its highlighting to the JavaFxGUI error handler.
             */
            HandlerJavaFxGUIError handlerJavaError = HandlerJavaFxGUIError.getInstance();

            switch (e.getField()) {
                case EMAIL -> {
                    handlerJavaError.highlightMissingFormElement(this.emailField);
                }
                case PASSWORD -> {
                    handlerJavaError.highlightMissingFormElement(this.pwdField);
                }
            }
        }
        catch (ApplicationException e){
            HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();
            handlerJavaFxGUIError.errorMessage(e.getMessage());
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Log in failed: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);

        }
    }

    @FXML
    public void signUp(){
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
        }
        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Failed to navigate to the sign-up page: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);
        }
    }

    @FXML
    public void continueWithGoogle(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Google log-in is not implemented yet.");
        alert.showAndWait();
    }

    @FXML
    public void continueWithFacebook(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Facebook log-in is not implemented yet.");
        alert.showAndWait();
    }


}
