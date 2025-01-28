package org.example.looseCoupling;

public class UserSqlDatabase implements UserDataProvider{
    @Override
    public String getUserDetails(){
        return "User Details from Sql Database";
    }
}
