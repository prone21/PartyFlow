package it.uniroma2.partyflow.model;

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

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pwd = pwd;
        this.dateOfBirth = dateOfBirth;
        this.accountType = accountType;
        this.gender = gender;

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

    public void setPwd (String pwd){
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
