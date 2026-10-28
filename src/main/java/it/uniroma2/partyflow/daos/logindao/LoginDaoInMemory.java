package it.uniroma2.partyflow.daos.logindao;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;

public class LoginDaoInMemory implements LoginDaoInterface {

    private final LoginCredential cred;

    public LoginDaoInMemory(LoginCredential cred) {
        this.cred = cred;
    }

    @Override
    public AccountType login() {

        System.out.println(
                "Dummy login completed for: " + cred.getEmail()
        );

        return AccountType.Participant;
    }
}