package it.uniroma2.partyflow.graphic_controller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.signUpCredentialsBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private RadioButton famaleOption;

    @FXML
    private RadioButton pntsOption;

    @FXML
    private RadioButton partyPlannerOption;

    @FXML
    private RadioButton participantOption;


    @FXML
    public void createAccount() {

        signUpCredentialsBean signUpBean = new signUpCredentialsBean();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");


        signUpBean.setName(nameField.getText());
        signUpBean.setSurname(surnameField.getText());
        signUpBean.setDateOfBirth(LocalDate.parse(dateField.getText(),formatter));
        signUpBean.setEmail(emailField.getText());
        signUpBean.setPassword(pwdField.getText());

        if (maleOption.isSelected()) {
            signUpBean.setGender(Gender.MALE);
        } else if (famaleOption.isSelected()) {
            signUpBean.setGender(Gender.FEMALE);
        } else if (pntsOption.isSelected()) {
            signUpBean.setGender(null);
        }

        if (partyPlannerOption.isSelected()) {
            signUpBean.setAccountType(AccountType.PartyPlanner);
        } else if (participantOption.isSelected()) {
            signUpBean.setAccountType(AccountType.Participant);
        } else {
            signUpBean.setAccountType(AccountType.NULLO);
        }

        SignUpController signUpController = new SignUpController();
        signUpController.createAccount(signUpBean);
    }

    public void goBackward() throws IOException {
        NavigatorSingleton nav = NavigatorSingleton.getInstance();
        nav.goBackward();
    }
}
