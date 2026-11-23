package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;



/**
 * In-memory implementation of the Data Access Object (DAO)
 * responsible for managing user persistence.
 *
 * <p>Provides operations for user registration and authentication
 * using the shared in-memory data store provided by InMemoryDB.</p>
 */


public class UserDaoInMemory implements UserDaoInterface {

    private final InMemoryDB memory = InMemoryDB.getInstance();


    /**
     * Checks whether a user with the same email address
     * already exists in the in-memory database.
     *
     * @param user the user to check
     * @return true if the user exists, false otherwise
     */

    private boolean exists(User user){
        return this.memory.read(user) != null;
    }


    /**
     * Persists a new user in the in-memory database.
     *
     * <p>Before storing the user, checks whether another user
     * with the same email address already exists.</p>
     *
     * <p>If a duplicate user is detected, the exception is logged
     * through HandlerSystemError and propagated to the caller.</p>
     *
     * @param user the user to persist
     * @throws DuplicateEntityException if the user already exists
     */

    @Override
    public void signUp(User user) throws DuplicateEntityException {

        try{
            if(this.exists(user)){
                throw new DuplicateEntityException();
            }

            this.memory.write(user);
        }

        catch (DuplicateEntityException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

    }


    /**
     * Authenticates a user against the in-memory database.
     *
     * <p>Retrieves the stored user by email address and verifies
     * the provided password against the stored password hash
     * through PasswordProtector.</p>
     *
     * <p>If authentication succeeds, returns the corresponding account type.
     * Otherwise, the exception is logged through HandlerSystemError
     * and propagated to the caller.</p>
     *
     * @param cred the login credentials provided by the user
     * @return the account type of the authenticated user
     * @throws EntityNotFound if the user does not exist or the password is incorrect
     */

    @Override
    public AccountType login(LoginCredential cred) throws EntityNotFound {
        try{
            User loggedUser = this.memory.findUserByEmail(cred.getEmail());

            if( (loggedUser == null) || !(PasswordProtector.checkPassword(cred.getPassword(),loggedUser.getPwd())) ){
                throw new EntityNotFound();
            }
            else {

                return loggedUser.getAccountType();
            }
        }

        catch (EntityNotFound e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }
    }

    @Override
    public User retriveUserByEmail(String email){
        return null;
    }


}
