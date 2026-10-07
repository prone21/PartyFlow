package it.uniroma2.partyflow.graphic_controller.logingraphiccontroller;

import it.uniroma2.partyflow.app_controller.LoginappController;
import it.uniroma2.partyflow.beans.LoginCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static java.lang.System.exit;

public class LoginGraphicControllerGUI {

     @FXML
     private TextField emailField;
     @FXML
     private TextField pwdField;


    @FXML
    public void goBackward() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.gotoView("/it/uniroma2/partyflow/view/view_in_common/firstScene.fxml");
    }

    @FXML
    public void submitCredentials() throws SQLException, NoSuchAlgorithmException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        String email = emailField.getText();
        String pwd = pwdField.getText();

        LoginCredentialsBean loginBean = new LoginCredentialsBean();
        loginBean.setEmail(email);
        loginBean.setPwd(pwd);

        LoginappController loginController = new LoginappController();
        AccountType accountType = loginController.login(loginBean);
        try{
            switch(accountType){
                case Participant:
                    nav.gotoView("/it/uniroma2/partyflow/view/Participant/participantHomePage.fxml");
                    break;

                case PartyPlanner:
                    nav.gotoView("/it/uniroma2/partyflow/view/Party_Planner/partyPlannerHomePage.fxml");
                    break;

                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Account non esistente, registrati!");
                    alert.showAndWait();
                    break;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            exit(0);
        }
    }

    @FXML
    public void signUp(MouseEvent event) throws IOException {
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
