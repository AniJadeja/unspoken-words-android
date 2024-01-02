package com.penofdreams.unspokenwords.firebase;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public static final int RC_SIGN_IN = 101;
    public static final int RC_SIGN_OUT = 102;
    private Activity activity;

    // Define a callback interface

    public interface AuthListener {
        void onAuthStateChanged(int authState);

        void onFailedAuth();
    }

    public Authentication(Activity activity) {
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
    }

    public boolean isUserLoggedIn() {
        currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }

    public FirebaseUser signIn(String email, String password, final AuthListener callback) {
        currentUser = null;

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("Flow -> Authentication", "signInWithEmail:success");
                currentUser = mAuth.getCurrentUser();
                if (callback != null) {
                    callback.onAuthStateChanged(RC_SIGN_IN);
                }
            } else {
                if (callback != null) {
                    callback.onFailedAuth();
                }
                // If sign in fails, display a message to the user.
                Log.w("Flow -> Authentication", "signInWithEmail:failure", task.getException());
            }
        });
        return currentUser;
    }

    public void signOut(final AuthListener callback) {
        mAuth.signOut();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) callback.onAuthStateChanged(RC_SIGN_OUT);
        else callback.onFailedAuth();
    }
}
