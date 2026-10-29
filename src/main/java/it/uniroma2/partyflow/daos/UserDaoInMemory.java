package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class UserDaoInMemory implements UserDaoInterface{

    @Override
    public boolean signUp(User user){
        return true;
    }

    @Override
    public AccountType login(LoginCredential cred){
        return null;
    }
}
