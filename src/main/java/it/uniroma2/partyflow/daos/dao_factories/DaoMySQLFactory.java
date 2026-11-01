package it.uniroma2.partyflow.daos.dao_factories;

import it.uniroma2.partyflow.daos.UserDaoInterface;
import it.uniroma2.partyflow.daos.UserDaoMySQL;
import it.uniroma2.partyflow.daos.logindao.LoginDaoMySQL;
import it.uniroma2.partyflow.model.LoginCredential;
import it.uniroma2.partyflow.model.User;

public class DaoMySQLFactory extends DaoAbstractFactory {
    private static DaoMySQLFactory instance;

    private DaoMySQLFactory(){};

    public static DaoMySQLFactory getInstance(){
        if(instance == null){
            instance = new DaoMySQLFactory();
        }
        return instance;
    }

    public UserDaoInterface getUserDao(){
        return new UserDaoMySQL();
    }

    /*
    public SignUpDaoInterface getSignUpDao(User accountInfo){
        return new SignUpDaoMySQL(accountInfo);
    }

    public LoginDaoInterface getLoginDao(LoginCredential cred){
        return new LoginDaoMySQL(cred);

     */

}
