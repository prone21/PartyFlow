package it.uniroma2.partyflow.dao.logindao;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;

public class LoginDaoNoPersistence implements LoginDaoInterface {

    private final LoginCredential cred;

    public LoginDaoNoPersistence(LoginCredential cred) {
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