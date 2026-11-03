package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

import java.util.List;

public class DemoMemory {

    // -----------------Singleton----------------------
    private static DemoMemory instance = null;

    private DemoMemory(){}

    public static DemoMemory getInstance(){
        if(instance == null){
            instance = new DemoMemory();
        }

        return instance;
    }
    // ------------------------------------------------


    // -------------------Users----------------------
    private List<User> users = null;

    public void write (User user) throws DuplicateEntityException{

        if (this.read(user.getEmail()) == null) {
            this.users.add(user);
        }
        else{
            throw new DuplicateEntityException();
        }

    }

    private User read (String email) {
        for (User user: this.users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }

        return null;
    }


    public AccountType read (LoginCredential cred) throws EntityNotFound{
        for (User user: this.users){
            if(user.getEmail().equals(cred.getEmail())){
                return user.getAccountType();
            }
        }

        throw new EntityNotFound();
    }

    // ----------------------------------------------




}
