package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.instagram.TSGInstagramManager;
import com.tsg.kiwitech.tsgsocialsharing.instagram.Constants;

import httputility.tsg.com.tsghttpcontroller.HttpResponse;
import httputility.tsg.com.tsghttpcontroller.HttpUtils;


public class InstagramActivity extends AppCompatActivity implements View.OnClickListener, HttpUtils.RequestCallBack {


    private final static String CLIENT_ID = "5a3f5995eca74eb7b1a348615638f96e";
    private final static String CLIENT_SECRET = "4255819d991d4f258d565af6b66d5daa";
    private final static String REDIRECT_URL = "https://www.facebook.com";

    private String access_token;

    private Button btnAuthorise, btnGetProfile, btnGetUserProfile, btnSelfRecentMedia, btnUserRecentMedia, btnFollows, btnFollowedBy, btnLogout, btnShareImageUsingIntent, btnShareVideoUsingIntent;
    private LinearLayout optionsRootLayou;
    private TSGInstagramManager tsgInstagramManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        btnAuthorise = (Button) findViewById(R.id.btnAuthorise);
        btnGetProfile = (Button) findViewById(R.id.btnGetProfile);
        btnShareImageUsingIntent = (Button) findViewById(R.id.btnShareImageUsingIntent);
        btnShareVideoUsingIntent = (Button) findViewById(R.id.btnShareVideoUsingIntent);
        btnGetUserProfile = (Button) findViewById(R.id.btnUserProfile);
        btnSelfRecentMedia = (Button) findViewById(R.id.btnSelfRecentMedia);
        btnUserRecentMedia = (Button) findViewById(R.id.btnUserRecentMedia);
        btnFollows = (Button) findViewById(R.id.btnFollows);
        btnFollowedBy = (Button) findViewById(R.id.btnFollowedBy);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        optionsRootLayou = (LinearLayout) findViewById(R.id.options_root_layout);

        btnAuthorise.setOnClickListener(this);
        btnGetProfile.setOnClickListener(this);
        btnShareImageUsingIntent.setOnClickListener(this);
        btnShareVideoUsingIntent.setOnClickListener(this);
        btnGetUserProfile.setOnClickListener(this);
        btnSelfRecentMedia.setOnClickListener(this);
        btnUserRecentMedia.setOnClickListener(this);
        btnFollows.setOnClickListener(this);
        btnFollowedBy.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        setButtonState();
    }

    private void setButtonState() {
        if (access_token != null) {
            btnAuthorise.setVisibility(View.GONE);
            optionsRootLayou.setVisibility(View.VISIBLE);
        } else {
            btnAuthorise.setVisibility(View.VISIBLE);
            optionsRootLayou.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAuthorise) {
            tsgInstagramManager = new TSGInstagramManager(this, CLIENT_ID, CLIENT_SECRET, REDIRECT_URL);
            tsgInstagramManager.authorize(new TSGInstagramManager.InstagramAuthListener() {
                @Override
                public void onSuccess(String access_token) {
                    InstagramActivity.this.access_token = access_token;
                    setButtonState();
                }

                @Override
                public void onError(String error) {
                    setButtonState();
                }

                @Override
                public void onCancel() {
                    setButtonState();
                }
            });
        } else if (v == btnGetProfile) {
            tsgInstagramManager.getSelfProfile(Constants.REQUEST_TYPE_GET_PROFILE, access_token, this);
        } else if (v == btnShareImageUsingIntent) {
            String path = "/sdcard/DCIM/Camera/20160121_105245.jpg";
            TSGInstagramManager.shareImageUsingIntent(this,"image/*",path);
        } else if (v == btnShareVideoUsingIntent) {
            String path = "/sdcard/DCIM/Camera/20160121_105234.mp4";
            TSGInstagramManager.shareVideoUsingIntent(this,"video/*",path);
        } else if (v == btnGetUserProfile) {
            tsgInstagramManager.getUserProfile(Constants.REQUEST_TYPE_GET_USER_PROFILE, "3314177362", access_token, this);
        } else if (v == btnSelfRecentMedia) {
            tsgInstagramManager.getSelfRecentMedia(Constants.REQUEST_TYPE_GET_RECENT_MEDIA, access_token, this);
        } else if (v == btnUserRecentMedia) {
            tsgInstagramManager.getUserRecentMedia(Constants.REQUEST_TYPE_GET_USER_RECENT_MEDIA, "3314177362", access_token, this);
        } else if (v == btnFollows) {
            tsgInstagramManager.getFollows(Constants.REQUEST_TYPE_GET_FOLLOWS, access_token, this);
        } else if (v == btnFollowedBy) {
            tsgInstagramManager.getFollowedBy(Constants.REQUEST_TYPE_GET_FOLLOWED_BY, access_token, this);
        } else if (v == btnLogout) {
            tsgInstagramManager.logout(this);
            tsgInstagramManager = null;
            access_token = null;
            setButtonState();
        }
    }


    @Override
    public void onSuccess(String s, HttpResponse httpResponse) {
        String response = httpResponse.getBody();
        if (response != null && !response.equals("")) {
            Toast.makeText(InstagramActivity.this, s + " Successful : " + response, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(String s, Throwable throwable, HttpResponse httpResponse) {
        Toast.makeText(InstagramActivity.this, "failed : " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish(String s) {

    }
}
