package com.example.joinme.database;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Encapsulates methods for accessing Firebase
 */
public class FirebaseAPI {

    private static final String TAG = "FirebaseAPI";

    // root
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    // User
    public static DatabaseReference userRef = rootRef.child("User");

    // Firebase auth
    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static void getFirebaseData(String nodePath, ValueEventListener valueEventListener) {
        DatabaseReference dbRef = rootRef.child(nodePath);
        // Read from the database
        dbRef.addValueEventListener(valueEventListener);
    }
    public static void getFirebaseDataOnce(String nodePath, ValueEventListener valueEventListener) {
        DatabaseReference dbRef = rootRef.child(nodePath);
        // Read from the database
        dbRef.addListenerForSingleValueEvent(valueEventListener);
    }
    public static Task<Void> setFirebaseData(String nodePath, String newData) {
        return rootRef.child(nodePath).setValue(newData);
    }

    /**
     * Push to a given Firebase node
     * @return unique key
     */
    public static String addFirebaseData(String nodePath, Object data, OnCompleteListener completionListener) {
        String key = rootRef.child(nodePath).push().getKey();
        rootRef.child(nodePath+"/"+key).setValue(data).addOnCompleteListener(completionListener);
        return key;
    }


    public static Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email,password);
    }

    public static Task<AuthResult> signUp(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public static FirebaseUser getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "getUser UID : " + currentUser.getUid());
        return currentUser;
    }



}
