package it.uniroma2.partyflow.utilities;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.systemexception.InternalException;


/**
 * Represents the session of the currently authenticated user.
 *
 * <p>Implements the Singleton design pattern to maintain a single
 * active user session within the application.</p>
 *
 * <p>Stores the authenticated user's email address and account type,
 * making session information accessible throughout the application
 * without requiring repeated queries to the persistence layer.</p>
 *
 * <p><b>Design note:</b> Although a future migration to a distributed
 * architecture is possible, the Singleton pattern is adopted because
 * the current standalone application supports only one active user
 * session at a time.</p>
 */

public class UserSession {
    private static UserSession instance = null;
    private final String userEmail;
    private final AccountType userAccountType;

    private UserSession(String userEmail, AccountType userAccountType) {
        this.userEmail = userEmail;
        this.userAccountType = userAccountType;
    }

    /**
     * Initializes the user session with the specified authentication information.
     *
     * <p>Creates a new Singleton instance only if no active session exists.
     * If a session has already been initialized, the existing instance
     * remains unchanged.</p>
     *
     * @param userEmail the email address of the authenticated user
     * @param userAccountType the account type of the authenticated user
     */
    public static void initializeSession(String userEmail, AccountType userAccountType) {
        if (instance == null) {
            instance = new UserSession(userEmail, userAccountType);
        }
    }

    /**
     * Retrieves the currently initialized user session.
     *
     * <p>Returns the existing Singleton instance without creating
     * a new session.</p>
     *
     * @return the current user session instance
     * @throws InternalException if the user session has not been initialized
     */
    public static UserSession getInstance() throws InternalException {
        if(instance == null){
            throw new InternalException("User session is not initialized.");
        }
        else{
            return instance;
        }
    }

    /**
     * Returns the email address of the authenticated user.
     *
     * @return the authenticated user's email address
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Returns the account type of the authenticated user.
     *
     * @return the authenticated user's account type
     */
    public AccountType getUserAccountType() {
        return userAccountType;
    }


    /**
     * Logs out the currently authenticated user.
     *
     * <p>Invalidates the active user session by setting the Singleton
     * instance to null, allowing a new session to be created
     * upon the next successful authentication.</p>
     */
    public static void logOut() {
        instance = null;
    }


}
