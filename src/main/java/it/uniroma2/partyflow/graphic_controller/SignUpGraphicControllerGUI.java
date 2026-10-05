package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.CredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.exceptions.EmptyException;
import it.uniroma2.partyflow.exceptions.SignUpException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class SignUpGraphicControllerGUI {

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField pwdField;

    @FXML
    private RadioButton maleOption;

    @FXML
    private RadioButton femaleOption;

    @FXML
    private RadioButton pntsOption;

    @FXML
    private RadioButton partyPlannerOption;

    @FXML
    private RadioButton participantOption;


    private final TextField[] fields = {
            nameField,
            surnameField,
            dateField,
            emailField,
            pwdField
    };



    public void goBackward() {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.goBackward();
    }

    @FXML
    public void createAccount() throws SQLException {
        try {
            CredentialsBean signUpBean = new CredentialsBean();

            signUpBean.setName(nameField.getText());
            signUpBean.setSurname(surnameField.getText());
            signUpBean.setDateOfBirth(dateField.getText());


            signUpBean.setEmail(emailField.getText());
            signUpBean.setPassword(pwdField.getText());

            if (maleOption.isSelected()) {
                signUpBean.setGender(Gender.MALE);
            } else if (femaleOption.isSelected()) {
                signUpBean.setGender(Gender.FEMALE);
            } else if (pntsOption.isSelected()) {
                signUpBean.setGender(Gender.PNTS);
            }

            if (partyPlannerOption.isSelected()) {
                signUpBean.setAccountType(AccountType.PartyPlanner);
            } else if (participantOption.isSelected()) {
                signUpBean.setAccountType(AccountType.Participant);
            }

            SignUpController signUpController = new SignUpController();
            signUpController.createAccount(signUpBean);
        }

        catch (SignUpException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        catch (EmptyException e){
            String style = "-fx-border-color: red;" +
                    "-fx-background-color: transparent;" + "-fx-font-size: 18px;"+
                    "-fx-prompt-text-fill:  #666666;";

            this.fields[e.getType()].setStyle(style);
        }

    }

}
