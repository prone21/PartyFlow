package it.uniroma2.partyflow.daos.dao_interfaces;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;

/**
 * Defines the operations for managing user persistence.
 * Implementations provide the persistence-specific logic.
 */


public interface UserDaoInterface {

    /**
     * Persists a new user account.
     *
     * @param user the user to be registered
     * @throws PersistenceGenericException if an unexpected
     *         persistence error occurs
     * @throws PersistenceServiceUnavailableException if the
     *         persistence service is unavailable
     * @throws DuplicateEntityException if the user already exists
     * @throws PersistenceConstraintViolationException if a
     *         persistence constraint is violated
     * @throws InvalidPersistenceTypeException if the configured
     *         persistence type is incompatible with the type
     *         required by the operation
     */
    void signUp(User user) throws PersistenceGenericException, PersistenceServiceUnavailableException,
            DuplicateEntityException, PersistenceConstraintViolationException, InvalidPersistenceTypeException;


    /**
     * Retrieves the account type associated with the
     * provided login credentials.
     *
     * @param cred the credentials used to identify the user
     * @return the account type associated with the user,
     *         or null if no matching account is found
     * @throws PersistenceServiceUnavailableException if the
     *         persistence service is unavailable
     * @throws EntityNotFound if no matching account is found
     * @throws PersistenceConstraintViolationException if a
     *         persistence constraint is violated
     * @throws PersistenceGenericException if an unexpected
     *         persistence error occurs
     * @throws InvalidPersistenceTypeException if the configured
     *         persistence type is incompatible with the type
     *         required by the operation
     */
    AccountType login(LoginCredential cred) throws PersistenceServiceUnavailableException, EntityNotFound, PersistenceConstraintViolationException,
            PersistenceGenericException, InvalidPersistenceTypeException;

    /**
     * Retrieves a user from the persistence layer using their email address.
     *
     * @param email the email address identifying the user
     * @return the User corresponding to the specified email
     * @throws PersistenceServiceUnavailableException if the persistence service is unavailable
     * @throws InvalidPersistenceTypeException if the configured persistence type is incompatible with the type
     * required by the operation
     * @throws PersistenceGenericException if an unexpected persistence error occurs
     * @throws EntityNotFound if no matching account is found
     */

    User retriveUserByEmail(String email) throws PersistenceServiceUnavailableException, InvalidPersistenceTypeException, PersistenceGenericException, EntityNotFound;
}
