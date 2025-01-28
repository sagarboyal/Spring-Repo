package org.example.tightCoupling;

// Why is this tightly coupled?
// 1. The User Manager class depends directly on the User Database class.
// 2. If we want to replace User Database with another database (like sql to mongoDB), we need to modify the User Manager class also.
public class TightCoupling {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        System.out.println(userManager.getUserInfo());
    }
}
