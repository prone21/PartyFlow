package it.uniroma2.partyflow.models;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import java.time.LocalDate;

public class User {
    private String name;
    private String surname;
    private String email;
    private String pwd;
    private LocalDate dateOfBirth;
    private AccountType accountType;
    private Gender gender;


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
        this.pwd = pwd;
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
