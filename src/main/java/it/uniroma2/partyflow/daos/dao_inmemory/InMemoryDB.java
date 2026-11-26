package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
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
     * Stores a new user in the in-memory database.
     *
     * <p>Checks whether a user with the same email address
     * already exists before adding the new user to the list.</p>
     *
     * <p>If a duplicate email is detected, a DuplicateEntityException
     * is thrown and the user is not stored.</p>
     *
     * @param user the user to store
     * @throws DuplicateEntityException if a user with the same email already exists
     */
    public void writeUser(User user) throws DuplicateEntityException {
        if(this.readUser(user.getEmail()) == null){
            this.users.add(user);
        }

        else{
            throw new DuplicateEntityException();
        }
    }


    /**
     * Retrieves a user from the in-memory database using their email address.
     *
     * <p>Searches the stored users and returns a new User instance
     * containing the same data as the matching user.</p>
     *
     * <p>Returning a copy prevents modifications to the retrieved
     * object from directly affecting the stored user.</p>
     *
     * @param email the email address identifying the user
     * @return a copy of the matching User, or null if no user is found
     */
    public User readUser(String email) {
        for (User u : this.users){
            if (u.getEmail().equals(email)){
                return new User(
                        u.getName(),
                        u.getSurname(),
                        u.getEmail(),
                        u.getPwd(),
                        u.getDateOfBirth(),
                        u.getAccountType(),
                        u.getGender());
            }
        }

        return null;
    }

    // ----------------------------------------------


}
