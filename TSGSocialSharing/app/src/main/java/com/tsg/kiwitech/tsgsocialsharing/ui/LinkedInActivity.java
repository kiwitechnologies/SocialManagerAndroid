package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.linkedInUtility.Constants;
import com.tsg.kiwitech.tsgsocialsharing.linkedInUtility.TSGLinkedInManager;

public class LinkedInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnLogout, btnGetProfile, btnSharePost, btnGetProfileAdditionalInfo, btnGetCompanyInfo, btnShareContent;
    private LinearLayout optionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnGetProfile = (Button) findViewById(R.id.btnGetProfile);
        btnSharePost = (Button) findViewById(R.id.btnSharePost);
        btnShareContent = (Button) findViewById(R.id.btnShareContent);
        btnGetProfileAdditionalInfo = (Button) findViewById(R.id.btnGetProfileAdditionalInfo);
        btnGetCompanyInfo = (Button) findViewById(R.id.btnGetCompanyInfo);
        optionsLayout = (LinearLayout) findViewById(R.id.options_root_layout);


        btnLogin.setOnClickListener(this);
        btnGetProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnGetProfileAdditionalInfo.setOnClickListener(this);
        btnGetCompanyInfo.setOnClickListener(this);
        btnSharePost.setOnClickListener(this);
        btnShareContent.setOnClickListener(this);

        updateView();
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            TSGLinkedInManager.login(this, new AuthListener() {
                @Override
                public void onAuthSuccess() {
                    updateView();
                }

                @Override
                public void onAuthError(LIAuthError error) {
                    updateView();
                }
            });
        } else if (v == btnGetProfile) {
            TSGLinkedInManager.getProfile(this, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    Toast.makeText(LinkedInActivity.this, apiResponse.getResponseDataAsString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    Toast.makeText(LinkedInActivity.this, LIApiError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnGetProfileAdditionalInfo) {
            TSGLinkedInManager.getProfileAdditionalInfo(this, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    Toast.makeText(LinkedInActivity.this, apiResponse.getResponseDataAsString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    Toast.makeText(LinkedInActivity.this, LIApiError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnGetCompanyInfo) {
            TSGLinkedInManager.getCompanyInfo(this, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    Toast.makeText(LinkedInActivity.this, apiResponse.getResponseDataAsString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    Toast.makeText(LinkedInActivity.this, LIApiError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnSharePost) {
            TSGLinkedInManager.sharePost(this, "Check out  this link :) developer.linkedin.com! http://linkd.in/1FC2PyG\"", new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    Toast.makeText(LinkedInActivity.this, apiResponse.getResponseDataAsString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    Toast.makeText(LinkedInActivity.this, LIApiError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnShareContent) {
            String comment = "Check out developer.linkedin.com!";
            String title = "LinkedIn Developers Resources";
            String description = "Leverage LinkedIn's APIs to maximize engagement";
            String submittedURL = "https://developer.linkedin.com";
            String submittedImageURL = "https://example.com/logo.png";
            TSGLinkedInManager.shareContentPost(this, comment, title, description, submittedURL, submittedImageURL, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    Toast.makeText(LinkedInActivity.this, apiResponse.getResponseDataAsString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError LIApiError) {
                    Toast.makeText(LinkedInActivity.this, LIApiError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnLogout) {
            TSGLinkedInManager.logout(this);
            updateView();
        }
    }

    private void updateView() {
        if (isLogedIn()) {
            btnLogin.setVisibility(View.GONE);
            optionsLayout.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            optionsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
    }


    private boolean isLogedIn() {
        LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
        LISession session = sessionManager.getSession();
        return session.isValid();
    }

}
