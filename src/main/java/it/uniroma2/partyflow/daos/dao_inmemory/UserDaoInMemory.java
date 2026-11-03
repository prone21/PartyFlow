package it.uniroma2.partyflow.daos.dao_inmemory;

import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class UserDaoInMemory implements UserDaoInterface {

    @Override
    public void signUp(User user){
    }

    @Override
    public AccountType login(LoginCredential cred){
        return null;
    }
}
