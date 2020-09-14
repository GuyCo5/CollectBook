package com.afeka.collectbook;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileManager {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String name;
    private String email;
    public ProfileManager() {
        refreshUser();
    }

    public FirebaseUser refreshUser (){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            name = "" + currentUser.getDisplayName();
            email = currentUser.getEmail();
        }
        return currentUser;
    }

    public boolean isUserSignedIn(){
        if (currentUser != null){
            return true;
        }else{
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
