package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralized in-memory data store implementing Singleton pattern (GoF).
 *
 * Acts as a "RAM database" for the IN_MEMORY persistence type.
 * All InMemory DAOs read/write to this shared store.
 *
 * Data Lifecycle
 * Data exists only during application runtime. On application exit,
 * all data is lost. This is intentional for demo purposes.
 **/


public class InMemoryDB {

    // -----------------Singleton----------------------
    private static InMemoryDB instance = null;

    private InMemoryDB(){}

    public static InMemoryDB getInstance(){
        if(instance == null){
            instance = new InMemoryDB();
        }

        return instance;
    }
    // ------------------------------------------------

    // -------------------Users----------------------
    private List<User> users = new ArrayList<>();

    public void write (User user){
        this.users.add(user);
    }

    public User findUserByEmail(String email){
        for (User u : this.users){
            if (u.getEmail().equals(email)){
                return u;
            }
        }

        return null;
    }

public User read (User user) {
    for (User u : this.users){
        if (u.getEmail().equals(user.getEmail())){
            return u;
        }
    }

    return null;
}


public User read (LoginCredential cred) throws EntityNotFound{
    for (User user: this.users){
        if( (user.getEmail().equals(cred.getEmail())) && (user.getPwd().equals(cred.getPassword())) ){
            return user;
        }
    }

    throw new EntityNotFound();
}

// ----------------------------------------------


}
