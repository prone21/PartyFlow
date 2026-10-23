package it.uniroma2.partyflow.beans;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.enums.UserField;
import it.uniroma2.partyflow.exceptions.RequiredFieldException;
import it.uniroma2.partyflow.exceptions.InvalidFieldFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class UserBean {
    private String name;
    private String surname;
    private String dateOfBirth;
    private Gender gender;
    private String email;
    private String password;
    private AccountType accountType;

    public UserBean() {}


    public UserBean(String name, String surname, String dateOfBirth,
                    Gender gender, String email, String password,
                    AccountType accountType)
            throws RequiredFieldException, InvalidFieldFormatException {

        this.setName(name);
        this.setSurname(surname);
        this.setDateOfBirth(dateOfBirth);
        this.setGender(gender);
        this.setEmail(email);
        this.setPassword(password);
        this.setAccountType(accountType);
    }

// ---------------------Name------------------------
    public String getName() {
        return this.name;
    }

    public void setName(String name) throws RequiredFieldException {
        if(name == null || name.isEmpty()){
            throw new RequiredFieldException(UserField.NAME);
        }
        else {
            this.name = name;
        }
    }

// ----------------------------------------------------



// ---------------------Surname------------------------
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) throws RequiredFieldException {
        if(surname == null || surname.isEmpty()){
            throw new RequiredFieldException(UserField.SURNAME);
        }
        else{
            this.surname = surname;
        }

    }

// ----------------------------------------------------



// ------------------------Manage Date of Birth-------------------------
    private boolean isValidDate(String date) {
        /*
         * Defines the expected date format:
         * dd   = two-digit day
         * MM   = two-digit month
         * uuuu = four-digit year
         *
         * STRICT mode ensures that the date actually exists.
         */

        // declare the parser
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/uuuu") // define the date-format
                        .withResolverStyle(ResolverStyle.STRICT); // check the existence of the date

        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            // Thrown when the date doesn't respect the format
            return false;
        }

        return true;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws InvalidFieldFormatException, RequiredFieldException {
        if(dateOfBirth == null || dateOfBirth.isEmpty()){
            throw new RequiredFieldException(UserField.DATE_OF_BIRTH);
        }
        else if (this.isValidDate(dateOfBirth)){
            this.dateOfBirth = dateOfBirth;
        }
        else{
            throw new InvalidFieldFormatException("Invalid date format. Please use DD/MM/YYYY.");
        }
    }

// ----------------------------------------------------------------------


// --------------------------Manage email--------------------------------
    private boolean isValidEmail(String email) {
        // Verifies that the email has the structure: something@domain.ext
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) throws InvalidFieldFormatException, RequiredFieldException {
        if (email == null || email.isEmpty()){
            throw new RequiredFieldException(UserField.EMAIL);
        }
        else if (this.isValidEmail(email)){
            this.email = email;
        }
        else{
            throw new InvalidFieldFormatException( "Invalid email format. " +
                    "Please enter a valid email address (e.g., user@example.com).");
        }
    }

// -------------------------------------------------------------------


// -------------------------Manage password---------------------------

    private boolean isValidPwd(String pwd){
        return pwd.length()>=8;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) throws InvalidFieldFormatException, RequiredFieldException {
        if (password == null || password.isEmpty()){
            throw new RequiredFieldException(UserField.PASSWORD);
        }
        else if (this.isValidPwd(password)){
            this.password = password;
        }
        else{
            throw new InvalidFieldFormatException("The password must be at least 8 characters long.");
        }
    }

// --------------------------------------------------------------------


// ---------------------------Gender-----------------------------------
    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) throws RequiredFieldException {
        if (gender == null){
            throw new RequiredFieldException(UserField.GENDER);
        }
        else{
            this.gender = gender;
        }
    }

// ----------------------------------------------------------------------


// --------------------------AccountType---------------------------------

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) throws RequiredFieldException {
       if(accountType == null){
           throw new RequiredFieldException(UserField.ACCOUNT_TYPE);
       }
       else {
           this.accountType = accountType;
       }
    }

// ----------------------------------------------------------------------



}
