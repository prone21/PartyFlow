package it.uniroma2.partyflow.model;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class User {
    private String name;
    private String surname;
    private String email;
    private String pwd;
    private LocalDate dateOfBirth;
    private AccountType accountType;
    private Gender gender;

    public User(){}

    public User(String name, String surname, String email, String pwd,
                    LocalDate dateOfBirth, AccountType accountType, Gender gender){


        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setPwd(pwd);
        this.setDateOfBirth(dateOfBirth);
        this.setAccountType(accountType);
        this.setGender(gender);

    }

    private String hashPassword (String password) throws NoSuchAlgorithmException{

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes =
                digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    // GETTER
    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPwd(){
        return this.pwd;
    }

    public LocalDate getDateOfBirth(){
        return this.dateOfBirth;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Gender getGender() {
        return gender;
    }


    // SETTER
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPwd (String pwd)  {
        try {
            this.pwd = this.hashPassword(pwd);
        }
        catch (NoSuchAlgorithmException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
        }
    }

    public void setDateOfBirth (LocalDate date){
        this.dateOfBirth = date;
    }

    public void setAccountType (AccountType accountType){
        this.accountType = accountType;
    }

    public void setGender (Gender gender){
        this.gender = gender;
    }
}
