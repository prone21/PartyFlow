package it.uniroma2.partyflow.beans;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.exceptions.EmptyException;
import it.uniroma2.partyflow.exceptions.SignUpException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class CredentialsBean {
    private String name;
    private String surname;
    private String dateOfBirth;
    private Gender gender;
    private String email;
    private String password;
    private AccountType accountType;

    public CredentialsBean() {}

    public String getName() {
        return name;
    }

    public void setName(String name) throws EmptyException {
        if(name.isEmpty()){
            throw new EmptyException(0);
        }
        else {
            this.name = name;
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws EmptyException{
        if(surname.isEmpty()){
            throw new EmptyException(1);
        }
        else{
            this.surname = surname;
        }

    }

    //------------------------Manage Date of Birth-------------------------
    private boolean isValidDate(String date) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/uuuu") // define the date-format
                        .withResolverStyle(ResolverStyle.STRICT); // check the existence of the date

        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) throws SignUpException, EmptyException {
        if(dateOfBirth.isEmpty()){
            throw new EmptyException(2);
        }
        else if (this.isValidDate(dateOfBirth)){
            this.dateOfBirth = dateOfBirth;
        }
        else{
            throw new SignUpException("Invalid date format. Please use DD/MM/YYYY.",0);
        }
    }
    //---------------------------------------------------------------------


    //------------------------ Manage email--------------------------------
    private boolean isValidEmail(String email) {
        // Verifies that the email has the structure: something@domain.ext
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SignUpException, EmptyException {
        if (email.isEmpty()){
            throw new EmptyException(3);
        }
        else if (this.isValidEmail(email)){
            this.email = email;
        }
        else{
            throw new SignUpException("Invalid email",0);
        }
    }
    //----------------------------------------------------------------------


    // Manage password
    public String getPassword() {
        return this.password;
    }

    private boolean isValidPwd(String pwd){
        return pwd.length()>=8;
    }

    public void setPassword(String password) throws SignUpException, EmptyException {
        if (password.isEmpty()){
            throw new EmptyException(4);
        }
        else if (this.isValidPwd(password)){
            this.password = password;
        }
        else{
            throw new SignUpException("The password must be at least 8 characters long.",0);
        }
    }
    //----------------------------------------------------------------------



    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) throws EmptyException {
        if (gender == null){
            throw new EmptyException(5);
        }
        else {
            this.gender = gender;
        }
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) throws EmptyException {
       if(accountType == null){
           System.out.println("la puta madre que de pariò");
           throw new EmptyException(6);
       }
       else {
           this.accountType = accountType;
       }
    }
}
