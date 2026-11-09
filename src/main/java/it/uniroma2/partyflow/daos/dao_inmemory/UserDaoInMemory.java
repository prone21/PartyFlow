package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;

public class UserDaoInMemory implements UserDaoInterface {

    private InMemoryDB memory = InMemoryDB.getInstance();


    private boolean exists(User user){
        if (this.memory.read(user) == null){
            return false;
        }
        return true;
    }


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


}
