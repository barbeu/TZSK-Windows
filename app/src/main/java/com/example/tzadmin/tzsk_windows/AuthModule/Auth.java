package com.example.tzadmin.tzsk_windows.AuthModule;

/**
 * Created by tzadmin on 14.04.17.
 */

public class Auth {
    /*TODO fix AuthData*/
    public static int id = -1;
    public static String login = "";
    public static String passwd = "";
    public static int dateLastLogin = -1;

    public static void setAuth (int idAuth, String loginAuth, String passwdAuth, int dateLastLoginAuth) {
        id = idAuth;
        login = loginAuth;
        passwd = passwdAuth;
        dateLastLogin = dateLastLoginAuth;
    }
}
