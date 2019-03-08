package networking;

import database.manager.PasswordManager;

public class test {
    public static void main(String[] args) {
        String password1 = "pass";
        String password2 = "pass";
        String hash1 = PasswordManager.HashPassword(password1);
        String hash2 = PasswordManager.HashPassword(password2);
        System.out.println(hash1.equals(hash2));
    }
}
