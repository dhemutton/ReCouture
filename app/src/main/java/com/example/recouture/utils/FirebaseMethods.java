package com.example.recouture.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseMethods {

    private static FirebaseUser firebaseUser;

    /**
     * Helper class that implements firebase methods for use in other activities.
     */


    /**
     * Firebase method to get User's Uid.
     * @return the user's unique Uid.
     */
    public static String getUserUid() {
        if (firebaseUser == null) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return firebaseUser.getUid();
    }





}
