package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

/**
 * Defines the operations for managing user persistence,
 * including account registration and authentication.
 * Implementations provide the persistence-specific logic.
 */


public interface UserDaoInterface {

    /** signUp
     * Registers a new user account.
     *
     * @param user the user whose account must be registered.
     * @return true if the account is successfully created,
     *         false otherwise.
     */

    boolean signUp(User user);


    /** login
     * Authenticates a user and retrieves their account type.
     *
     * @param cred the credentials used to authenticate the user.
     * @return the AccountType associated with the authenticated user,
     *         or null if no matching account is found.
     */
    AccountType login(LoginCredential cred);
}
