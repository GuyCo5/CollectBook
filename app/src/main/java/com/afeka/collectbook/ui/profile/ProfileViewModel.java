package com.afeka.collectbook.ui.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private MutableLiveData<String> name;
    private MutableLiveData<String> email;

    public ProfileViewModel() {
        name = new MutableLiveData<>();
        email = new MutableLiveData<>();
        refreshUser();
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getEmail() {
        return email;
    }


    public FirebaseUser refreshUser (){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (isUserSignedIn()){
            name.setValue("" + currentUser.getDisplayName());
            email.setValue(currentUser.getEmail());
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
}