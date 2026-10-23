package it.uniroma2.partyflow.graphic_controller.signupgraphiccontroller;

import it.uniroma2.partyflow.app_controller.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.RequiredFieldException;
import it.uniroma2.partyflow.exceptions.InvalidFieldFormatException;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import it.uniroma2.partyflow.exceptions.InvalidStateException;

import java.security.NoSuchAlgorithmException;

public class SignUpGraphicControllerGUI {

    // TextField
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

    // ----------------------------------


    // RadioButton
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

    // ----------------------------------

    // AnchorPane/ToggleGroup
    @FXML
    private AnchorPane genderPane;

    @FXML
    private AnchorPane accountTypePane;

    // ----------------------------------

    // REVIEW da eliminare
    private Node errorField;
    // REVIEW da eliminare
    private String oldStyleField;

    public void goBackward() {
        try {
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            nav.goBackward();
        }
        catch (InvalidStateException e){
            HandlerSystemError handlerSysErr = HandlerSystemError.getInstance();
            handlerSysErr.logError(e);
        }
    }

    @FXML
    public void createAccount() throws NoSuchAlgorithmException {
        /* REVIEW DA TOGLIERE
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

         */


        try {

            // 1) create the bean where the user date will be memorized
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
            //---------------------------------------------


            // pass the UserBean to the application controller
            SignUpController signUpController = new SignUpController();
            signUpController.createAccount(signUpBean);
            //---------------------------------------------

        }

        catch (RequiredFieldException e){
            /*
             * Handles missing required fields in the sign-up form.
             * Uses a switch statement to identify the corresponding JavaFX element
             * and delegates its highlighting to the JavaFxGUI error handler.
             */

            HandlerJavaFxGUIError handlerJavaError = HandlerJavaFxGUIError.getInstance();

            switch (e.getField()){
                case NAME->{
                    handlerJavaError.highlightMissingFormElement(this.nameField);
                }
                case SURNAME -> {
                    handlerJavaError.highlightMissingFormElement(this.surnameField);
                }
                case DATE_OF_BIRTH -> {
                    handlerJavaError.highlightMissingFormElement(this.dateField);
                }
                case EMAIL -> {
                    handlerJavaError.highlightMissingFormElement(this.emailField);
                }
                case PASSWORD -> {
                    handlerJavaError.highlightMissingFormElement(this.pwdField);
                }
                case GENDER -> {
                    handlerJavaError.highlightMissingFormElement(this.genderPane);
                }
                case ACCOUNT_TYPE -> {
                    handlerJavaError.highlightMissingFormElement(this.accountTypePane);
                }
            }

        }

        catch (InvalidFieldFormatException e) {
            /*
             * Handles invalid field formats in the sign-up form.
             * Delegates the display of the exception message
             * to the JavaFX GUI error handler.
             */
            HandlerJavaFxGUIError handlerJavaError = HandlerJavaFxGUIError.getInstance();
            handlerJavaError.alertErrorMessage(e.getMessage());
        }


    }


}
