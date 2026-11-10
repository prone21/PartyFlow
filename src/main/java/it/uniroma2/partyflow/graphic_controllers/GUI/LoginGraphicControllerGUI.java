package it.uniroma2.partyflow.graphic_controllers.GUI;

import it.uniroma2.partyflow.app_controllers.LoginController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;


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
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
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

/*
        catch (InternalException | IOException e){
            HandlerSystemError handlerSysErr = HandlerSystemError.getInstance();
            HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();

            handlerSysErr.logError(e);
            handlerJavaFxGUIError.errorMessage("An unexpected internal error has occurred. " +
                    "The application cannot continue and will be terminated.");

            handlerSysErr.handleFatalError(e);
        }

        catch (ApplicationException e){
            HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();
            handlerJavaFxGUIError.errorMessage(e.getMessage());
        }

 */
    }

    @FXML
    public void signUp(MouseEvent event) throws IOException {
        //REVIEW MA CHE FAI
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        NavigatorSingleton nav = NavigatorSingleton.getInstance(stage);
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/signUp.fxml");
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
