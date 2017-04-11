package com.example.tzadmin.tzsk_windows.SaveAuthModule;

import android.content.Context;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by tzadmin on 11.04.17.
 */

public class SaveAuth {
    private static FileWriter writer = null;
    private static File file = null;
    public static String login = "login";
    public static String psswd = "psswd";
    public static String pachAuthData = "auth.cfg";

    public static void SetUp (Context context) {
        file = new File(context.getExternalFilesDir(null), pachAuthData);
    }

    public static boolean set (String login, String passwd) {
        try {
            SaveAuth.login = login;
            SaveAuth.psswd = passwd;

            SaveAuth.clear();
            writer = new FileWriter(file, false);
            writer.write(login + "&" + passwd);
            writer.flush();
            return true;
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public static void clear() {
        if(file != null)
            file.delete();
    }

    public static boolean get () {
        try {
            FileReader reader = new FileReader(file);
            String str = new String();
            int c;
            while((c=reader.read()) != -1){
                str += (char)c;
            }
            String [] strings = str.split("&");
            if(strings.length >= 2) {
                SaveAuth.login = strings[0];
                SaveAuth.psswd = strings[1];
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
