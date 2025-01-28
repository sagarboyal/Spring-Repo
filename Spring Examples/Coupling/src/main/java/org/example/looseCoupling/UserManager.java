package org.example.looseCoupling;

public class UserManager {

    private final UserDataProvider dataProvider;

    // Use dependency injection to achieve loose coupling
    public UserManager(UserDataProvider dataProvider) {
        this.dataProvider = dataProvider; // Loose coupling: Depends on abstraction (UserDataProvider), not a concrete class
    }

    public String getUserInfo(){
        return dataProvider.getUserDetails();
    }
}
