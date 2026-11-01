package it.uniroma2.partyflow.daos;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class UserDaoCSV implements UserDaoInterface{

    @Override
    public void signUp(User user){
    }

    @Override
    public AccountType login(LoginCredential cred){
        return null;
    }

}
