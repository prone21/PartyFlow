package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationConfigurationException;
import it.uniroma2.partyflow.exceptions.uiexception.PersistenceError;
import it.uniroma2.partyflow.exceptions.uiexception.ServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.uiexception.UserOperationException;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

/**
 * Defines the operations for managing user persistence,
 * including account registration and authentication.
 * Implementations provide the persistence-specific logic.
 */


public interface UserDaoInterface {    /**
     * Registers a new user by storing their personal information
     * and account details in the persistence layer.
     * If the registration fails due to a persistence error,
     * a PersistenceException is thrown.
     *
     * @param user the user to be registered
     * @throws UserOperationException if an error occurs during registration
     */

    void signUp(User user) throws PersistenceError;


    /** login
     * Authenticates a user and retrieves their account type.
     *
     * @param cred the credentials used to authenticate the user.
     * @return the AccountType associated with the authenticated user,
     *         or null if no matching account is found.
     */
    AccountType login(LoginCredential cred);
}
