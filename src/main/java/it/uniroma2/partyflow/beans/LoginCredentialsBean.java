package it.uniroma2.partyflow.beans;

import it.uniroma2.partyflow.enums.UserField;
import it.uniroma2.partyflow.exceptions.uiexception.RequiredFieldException;

/**
 * Bean responsible for storing user login credentials and performing syntactic checks.
 *
 * <p>Stores the email and password provided during authentication.
 * It performs syntactic checks to ensure that both fields are not null
 * or empty, throwing a RequiredFieldException when a mandatory field
 * is missing.</p>
 */

public class LoginCredentialsBean {
    private String email;
    private String password;

    // --------------------------Manage email--------------------------------
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) throws RequiredFieldException {
        if (email == null || email.isEmpty()){
            throw new RequiredFieldException(UserField.EMAIL);
        }
        else{
            this.email = email;
        }
    }
    // -------------------------------------------------------------------


    // -------------------------Manage password---------------------------
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) throws RequiredFieldException {
        if (password == null || password.isEmpty()){
            throw new RequiredFieldException(UserField.PASSWORD);
        }
        else{
            this.password = password;
        }
    }
    // --------------------------------------------------------------------




}
