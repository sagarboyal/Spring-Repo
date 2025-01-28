package org.example.IoC;

public class UserMongoDataBase implements UserDataProvider{
    @Override
    public String getUserDetails() {
        return "User Details from Mongo Database";
    }
}
