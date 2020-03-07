package com.library.app.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Auth {

    public static void initFirebase() {
        try {
            FileInputStream refreshToken = new FileInputStream("D:\\Projects\\library-googlebooks\\library\\src\\main\\resources\\library-gbooks-firebase-adminsdk.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://library-gbooks.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidToken() {
        return isValidToken(null);
    }

    public static boolean isValidToken(String idToken) {
        boolean isValid = false;

        try {
            if (FirebaseApp.getApps().size() == 1) {
                isValid = true;
            } else {
                if (idToken != null) {
                    initFirebase();
                    FirebaseAuth.getInstance().verifyIdToken(idToken, true);
                    isValid = true;
                }
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }

        return isValid;
    }

}
