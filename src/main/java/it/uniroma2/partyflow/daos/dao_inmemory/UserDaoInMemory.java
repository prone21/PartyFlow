package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.DuplicateEntityException;
import it.uniroma2.partyflow.exceptions.daoexception.EntityNotFound;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class UserDaoInMemory implements UserDaoInterface {

    private DemoMemory memory = DemoMemory.getInstance();

    @Override
    public void signUp(User user) throws DuplicateEntityException{
        try{
            this.memory.write(user);
        }
        catch (DuplicateEntityException e) {
            throw e;
        }

    }

    @Override
    public AccountType login(LoginCredential cred) throws EntityNotFound{
        try{
            return this.memory.read(cred);
        }
        catch (EntityNotFound e){
            throw e;
        }
    }
}
