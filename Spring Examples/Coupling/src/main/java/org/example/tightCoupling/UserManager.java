package org.example.tightCoupling;

public class UserManager {

    private final UserDatabase userDatabase = new UserDatabase(); // Tight coupling: UserManager depends directly on UserDatabase

    public String getUserInfo(){
        return userDatabase.getUserDetails();
    }
}
