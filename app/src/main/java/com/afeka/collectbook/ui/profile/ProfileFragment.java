package com.afeka.collectbook.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.afeka.collectbook.ProfileManager;
import com.afeka.collectbook.R;
import com.firebase.ui.auth.AuthUI;
import java.util.Arrays;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final int RC_SIGN_IN = 123;
    public static final int RESULT_OK = -1;

    private ProfileViewModel profileViewModel;
    private ProfileManager profileManager;
    private View root;
    private LayoutInflater inflater;
    private ViewGroup container;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        profileManager = new ProfileManager();
        inflateProfile();


        final TextView textView = root.findViewById(R.id.text_profile);
        return root;
    }

    public void inflateProfile(){

        if (profileManager.isUserSignedIn()){
            //load user profile
            profileViewModel =
                    ViewModelProviders.of(this).get(ProfileViewModel.class);
            root = inflater.inflate(R.layout.fragment_profile, container, false);
            final TextView textViewName = root.findViewById(R.id.text_profile);
            final TextView textViewEmail = root.findViewById(R.id.textViewEmail);
            profileViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textViewName.setText(s);
                }
            });
            profileViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textViewEmail.setText(s);
                }
            });
        }
        else{
            root = inflater.inflate(R.layout.fragment_anon, container, false);
            Button signInButton = root.findViewById(R.id.button_sign_in);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
//            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                profileViewModel.refreshUser();
                inflateProfile();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


}

