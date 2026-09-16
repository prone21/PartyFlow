package it.uniroma2.partyflow.beans;

public class loginCredentialsBean {
    private String email;
    private String pwd;

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.pwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }
}
