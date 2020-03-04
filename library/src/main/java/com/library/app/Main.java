package com.library.app;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final String UID = "smvIGMInMgXW1yaBcCUGlH2hKYJ2";

    public static void main(String[] args) {
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("D:\\Projects\\library-googlebooks\\library\\src\\main\\resources\\library-gbooks-firebase-adminsdk.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(refreshToken))
              .setDatabaseUrl("https://library-gbooks.firebaseio.com/")
              .build();

            FirebaseApp.initializeApp(options);
            String customToken = FirebaseAuth.getInstance().createCustomTokenAsync(UID).get();
            System.out.println(customToken);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
