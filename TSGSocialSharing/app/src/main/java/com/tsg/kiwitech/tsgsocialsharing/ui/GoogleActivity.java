package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.googleUtility.TSGGoogleManager;

public class GoogleActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private GoogleApiClient mGoogleApiClient;
    private LinearLayout optionsLayout;
    private SignInButton signInButton;
    private TSGGoogleManager tsgGoogleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);

        tsgGoogleManager = TSGGoogleManager.Init(getApplicationContext(), this, this);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(tsgGoogleManager.getScope());

        signInButton.setOnClickListener(this);
        findViewById(R.id.btnLogout).setOnClickListener(this);
        findViewById(R.id.btnRevokeAccess).setOnClickListener(this);
        optionsLayout = (LinearLayout) findViewById(R.id.options_root_layout);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                tsgGoogleManager.signIn(this,RC_SIGN_IN);
                break;
            case R.id.btnLogout:
                tsgGoogleManager.signOut(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(GoogleActivity.this, "Logout status: " + status.getStatus(), Toast.LENGTH_SHORT).show();
                        signInButton.setVisibility(View.VISIBLE);
                        optionsLayout.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.btnRevokeAccess:
                tsgGoogleManager.revokeAccess(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Toast.makeText(GoogleActivity.this, "Revoke access status: " + status.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this, "Successfully SignIn: " + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            signInButton.setVisibility(View.GONE);
            optionsLayout.setVisibility(View.VISIBLE);
        } else {
            // Signed out, re-login
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        An unresolvable error has occurred and Google APIs (including Sign-In) will not be available.
    }
}