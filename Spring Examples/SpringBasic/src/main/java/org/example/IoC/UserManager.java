package org.example.IoC;

public class UserManager {

    private final UserDataProvider dataProvider;

    // Use dependency injection to achieve loose coupling
    public UserManager(UserDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public String getUserInfo(){
        return dataProvider.getUserDetails();
    }
}
