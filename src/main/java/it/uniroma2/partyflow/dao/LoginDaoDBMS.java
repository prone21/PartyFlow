package it.uniroma2.partyflow.dao;
import java.sql.*;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import it.uniroma2.partyflow.session.SessionManager;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.model.LoginCredential;

public class LoginDaoDBMS {
    LoginCredential cred;

    public LoginDaoDBMS(LoginCredential cred){
        this.cred = cred;
    }

    public AccountType checkCredentials() throws SQLException {
        SessionManager sm = SessionManager.getSessionManager();
        Connection conn = sm.getConnection();

        String query = "select email from users where email = ? and password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1,cred.getEmail());
        stmt.setString(2,cred.getPassword());

        ResultSet rs = stmt.executeQuery();
        String accountType;
        if(rs.next()){
            accountType = rs.getString("accountType");
            return AccountType.valueOf(accountType);
        }
        else{
            return AccountType.AccountNotExsist;
        }

    }



}
