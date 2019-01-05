package com.innovvscript.bluediary;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainMenuActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = MainMenuActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        super.onStart();
    }

    public void signOut(View v) {
        Intent intent = getIntent();

        if (intent.getStringExtra("signed_in_with").equalsIgnoreCase("fb")) {
            LoginManager.getInstance().logOut();
            goBackToLoginPage();
        } else if (intent.getStringExtra("signed_in_with").equalsIgnoreCase("google")) {
            Log.d(TAG,"clicked");
           goBackToLoginPage();

                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                Log.d(TAG, "signedOut");
                                goBackToLoginPage();
                            }
                        });
        }
    }



    private void goBackToLoginPage() {
        Intent intent = new Intent(MainMenuActivity.this,MainActivity.class);
        intent.putExtra("just_signed_out",true);
        startActivity(intent);
        finish();
    }

}


