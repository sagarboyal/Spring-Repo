package org.example.IoC;

public class UserSqlDatabase implements UserDataProvider{
    @Override
    public String getUserDetails(){
        return "User Details from Sql Database";
    }
}
