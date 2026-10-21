package it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.enums.UIType;
import it.uniroma2.partyflow.enums.UserField;
import it.uniroma2.partyflow.errorhandlers.HandlerErrorFactory;
import it.uniroma2.partyflow.errorhandlers.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.errorhandlers.HandlerUIError;
import it.uniroma2.partyflow.exceptions.RequiredFieldException;
import it.uniroma2.partyflow.exceptions.SignUpException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import it.uniroma2.partyflow.utilities.configurators.UIConfigurator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import it.uniroma2.partyflow.exceptions.InvalidStateException;

import java.security.NoSuchAlgorithmException;
import java.util.EnumMap;
import java.util.Map;

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
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.goBackward();
        }
        catch (InvalidStateException e){
            HandlerErrorFactory factory = HandlerErrorFactory.getInstance();
            HandlerSystemError hse = factory.getHandlerSystemError();
            hse.severeLog("InvalidStateException",e);
        }
    }

    @FXML
    public void createAccount() throws NoSuchAlgorithmException {
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
            UserBean signUpBean = new UserBean();

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
            HandlerErrorFactory heFactory = HandlerErrorFactory.getInstance();
            HandlerUIError hUIError = heFactory.getHandlerUIError();
            hUIError.alertMessage(e.getMessage());
        }
        catch (RequiredFieldException e){
            UIConfigurator uiconf = UIConfigurator.getInstance();
            UIType uIType = uiconf.getUI();

            switch (uIType){
                case JavaFxGUI -> {
                    Map<UserField, Node> fields =  new EnumMap<>(UserField.class);
                    fields.put(UserField.NAME, this.nameField);
                    fields.put(UserField.SURNAME, this.surnameField);
                    fields.put(UserField.DATE_OF_BIRTH, this.dateField);
                    fields.put(UserField.EMAIL, this.emailField);
                    fields.put(UserField.PASSWORD, this.pwdField);
                    fields.put(UserField.GENDER, this.genderPane);
                    fields.put(UserField.ACCOUNT_TYPE, this.accountTypePane);

                    HandlerJavaFxGUIError handlerGui = HandlerJavaFxGUIError.getInstance();
                    handlerGui.highlightMissedParameter(fields.get(e.getField()));
                }

                case CLI -> {

                }
            }

        }

    }






}
