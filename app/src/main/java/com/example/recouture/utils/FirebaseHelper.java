package com.example.recouture.utils;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHelper {

    /**
     * Helper class that implements firebase methods for use in other activities.
     */


    /**
     * Firebase method to get User's Uid.
     * @return the user's unique Uid.
     */
    public static String getUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }





}
