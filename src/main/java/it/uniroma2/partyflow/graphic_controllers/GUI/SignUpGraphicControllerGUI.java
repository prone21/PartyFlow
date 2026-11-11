package it.uniroma2.partyflow.graphic_controllers.GUI;

import it.uniroma2.partyflow.app_controllers.SignUpController;
import it.uniroma2.partyflow.beans.UserBean;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.handlerui.HandlerJavaFxGUIError;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;
import it.uniroma2.partyflow.exceptions.uiexception.*;
import it.uniroma2.partyflow.utilities.NavigatorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

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


    // Gender toggle group
    @FXML
    private AnchorPane genderPane;

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton maleOption;

    @FXML
    private RadioButton femaleOption;

    @FXML
    private RadioButton pntsOption;

    // ----------------------------------


    // Account type toggle group
    @FXML
    private ToggleGroup accountTypeGroup;

    @FXML
    private AnchorPane accountTypePane;

    @FXML
    private RadioButton partyPlannerOption;

    @FXML
    private RadioButton participantOption;

    // ----------------------------------

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


    private Gender obtainSelectedGender() {
        /*
         * Toggle is an interface implemented by certain selectable JavaFX controls.
         * getSelectedToggle() returns a reference to the currently selected Toggle in the ToggleGroup, or null if none is selected.
         */

        Toggle selectedGender = genderGroup.getSelectedToggle();
        Gender gender = null;


        /*
         * In Java, the == operator compares object references, not their contents.
         * It returns true if both references point to the same object in memory
         * (conceptually, the same memory address).
         *
         * Java checks at compile time whether the reference types are compatible.
         * Here, selectedGender and maleOption can be compared because RadioButton
         * implements the Toggle interface through its superclass ToggleButton.
         *
         * The comparison checks whether both references point to the
         * same RadioButton instance.
         */

        if (selectedGender == maleOption) {
            gender = Gender.MALE;
        } else if (selectedGender == femaleOption) {
            gender = Gender.FEMALE;
        } else if (selectedGender == pntsOption) {
            gender = Gender.PNTS;
        }

        return gender;
    }

    private AccountType obtainSelectedAccountType(){
        Toggle selectedAccountType = accountTypeGroup.getSelectedToggle();
        AccountType account = null;

        if(selectedAccountType == partyPlannerOption){
            account = AccountType.PartyPlanner;
        } else if (selectedAccountType == participantOption) {
            account = AccountType.Participant;
        }

        return account;
    }

    @FXML
    public void createAccount(){

        try{
            // 1) create the bean where the user date will be memorized
            UserBean signUpBean = new UserBean();

            signUpBean.setName(nameField.getText());
            signUpBean.setSurname(surnameField.getText());
            signUpBean.setDateOfBirth(dateField.getText());


            signUpBean.setEmail(emailField.getText());
            signUpBean.setPassword(pwdField.getText());

            signUpBean.setGender(this.obtainSelectedGender());

            signUpBean.setAccountType(this.obtainSelectedAccountType());
            //---------------------------------------------


            // 2) pass the UserBean to the application controller
            SignUpController signUpController = new SignUpController();
            signUpController.createAccount(signUpBean);
            //---------------------------------------------

            // 3) show the right homepage
            NavigatorSingleton nav = NavigatorSingleton.getInstance();
            switch (signUpBean.getAccountType()){
                case PartyPlanner -> nav.gotoView("it/uniroma2/partyflow/view/Party_Planner/partyPlannerHomePage.fxml");
                case Participant -> nav.gotoView("it/uniroma2/partyflow/view/Participant/participantHomePage.fxml");
            }
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
            handlerJavaError.errorMessage(e.getMessage());
        }

        catch (ApplicationException e){
            HandlerJavaFxGUIError handlerJavaFxGUIError = HandlerJavaFxGUIError.getInstance();
            handlerJavaFxGUIError.errorMessage(e.getMessage());
        }

        catch (InternalException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError("Sign up failed: " + e.getMessage(), e);

            handlerSystemError.handleFatalError(e);

        }

    }


}
