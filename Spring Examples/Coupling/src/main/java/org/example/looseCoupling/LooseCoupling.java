package org.example.looseCoupling;

import org.example.looseCoupling.UserManager;


// Why is this loosely coupled?
// 1. The UserManager class depends on the abstraction (UserDataProvider), not specific implementations.
// 2. We can easily replace UserSqlDatabase with another implementation (like UserMongoDataBase) without modifying UserManager.
// 3. New services can be added by implementing UserDataProvider, maintaining scalability and flexibility.
public class LooseCoupling {
    public static void main(String[] args) {
        UserManager userManager = new UserManager(new UserSqlDatabase());
        UserManager userManager1 = new UserManager(new UserMongoDataBase());
        System.out.println(userManager.getUserInfo());
        System.out.println(userManager1.getUserInfo());
    }
}
