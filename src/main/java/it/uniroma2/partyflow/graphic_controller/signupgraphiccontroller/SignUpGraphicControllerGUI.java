package it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.CredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.exceptions.EmptyException;
import it.uniroma2.partyflow.exceptions.SignUpException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.security.NoSuchAlgorithmException;
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
    private PasswordField pwdField;

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

    @FXML
    private AnchorPane genderPane;

    @FXML
    private AnchorPane accountTypePane;

    private Node errorField;

    private String oldStyleField;

    public void goBackward() {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.goBackward();
    }

    @FXML
    public void createAccount() throws SQLException, NoSuchAlgorithmException {
         Node[] nodes = {
                 nameField,
                 surnameField,
                 dateField,
                 emailField,
                 pwdField,
                 genderPane,
                 accountTypePane
        };

        if (errorField != null){
            this.errorField.setStyle(this.oldStyleField);
        }

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
            else{
                signUpBean.setGender(null);
            }

            if (partyPlannerOption.isSelected()) {
                signUpBean.setAccountType(AccountType.PartyPlanner);
            } else if (participantOption.isSelected()) {
                signUpBean.setAccountType(AccountType.Participant);
            }
            else{
                System.out.println("e dio cane");
                signUpBean.setAccountType(null);
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
            this.oldStyleField = nodes[e.getType()].getStyle();
            System.out.println(e.getType());
            System.out.println(nodes[e.getType()].getId());
            nodes[e.getType()].setStyle(this.oldStyleField + "-fx-border-color: red;");
            this.errorField = nodes[e.getType()];

        }

    }

}
