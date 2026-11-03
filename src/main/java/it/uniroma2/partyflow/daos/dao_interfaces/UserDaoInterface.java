package it.uniroma2.partyflow.daos.dao_interfaces;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceConstraintViolationException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceGenericException;
import it.uniroma2.partyflow.exceptions.daoexception.PersistenceServiceUnavailableException;
import it.uniroma2.partyflow.exceptions.uiexception.ApplicationException;
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
     * @throws ApplicationException if an error occurs during registration
     */

    void signUp(User user) throws PersistenceGenericException, PersistenceServiceUnavailableException,
            DuplicateEntityException, PersistenceConstraintViolationException;


    /** login
     * Authenticates a user and retrieves their account type.
     *
     * @param cred the credentials used to authenticate the user.
     * @return the AccountType associated with the authenticated user,
     *         or null if no matching account is found.
     */
    AccountType login(LoginCredential cred) throws PersistenceServiceUnavailableException, DuplicateEntityException, PersistenceConstraintViolationException,
            PersistenceGenericException;
}
