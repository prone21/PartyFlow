package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralized in-memory data store implementing Singleton pattern (GoF).
 *
 * <p>Acts as a "RAM database" for the IN_MEMORY persistence type.
 * All InMemory DAOs read/write to this shared store.</p>
 *
 * <p><b>Data Lifecycle</b></p>
 *
 * <p>Data exists only during application runtime. On application exit,
 * all data is lost. This is intentional for demo purposes.</p>
 */


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
    private final List<User> users = new ArrayList<>();


    /**
     * Stores a user in the in-memory database.
     *
     * @param user the user to store
     */

    public void write (User user){
        this.users.add(user);
    }


    /**
     * Searches for a stored user by email address.
     *
     * @param email the email address identifying the user
     * @return the matching user, or null if no user is found
     */

    public User findUserByEmail(String email){
        for (User u : this.users){
            if (u.getEmail().equals(email)){
                return u;
            }
        }

        return null;
    }



    /**
     * Searches for a stored user using the email address
     * of the provided User object.
     *
     * @param user the user to search for
     * @return the matching stored user, or null if no user is found
     */

    public User read (User user) {
        for (User u : this.users){
            if (u.getEmail().equals(user.getEmail())){
                return u;
            }
        }

        return null;
    }


    // ----------------------------------------------


}
