package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.share.Sharer;
import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.facebookUtility.TSGFacebookManager;

import bolts.AppLinks;

public class FacebookActivity extends Activity implements View.OnClickListener {

    private LinearLayout llOptionsLayout;
    private Button btnFBLogin;
    private Button btnGetProfile;
    private Button btnPostTxtMsg;
    private Button btnImgMsg;
    private Button btnImgAndTxtMsg;
    private Button btnLinkedContent;
    private Button btnGetFriend;
    private Button btnInviteFriend;
    private Button btnLogout;
    private Button btnImgMsgInback;
    private Button btnImgAndTxtMsgInback;
    private Button btnLinkedContentInback;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_facebook);

        TSGFacebookManager.init(getApplicationContext());
        bmp = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();

        btnFBLogin = (Button) findViewById(R.id.btnFBLogin);
        llOptionsLayout = (LinearLayout) findViewById(R.id.options_root_layout);
        btnGetProfile = (Button) findViewById(R.id.btnGetProfile);
        btnPostTxtMsg = (Button) findViewById(R.id.btnPostTxtMsg);
        btnImgMsg = (Button) findViewById(R.id.btnPostImgMsg);
        btnImgMsgInback = (Button) findViewById(R.id.btnPostImgMsgInBack);
        btnImgAndTxtMsg = (Button) findViewById(R.id.btnPostImgAndTextMsg);
        btnImgAndTxtMsgInback = (Button) findViewById(R.id.btnPostImgAndTextMsgInBack);
        btnLinkedContent = (Button) findViewById(R.id.btnShareLinkedContent);
        btnLinkedContentInback = (Button) findViewById(R.id.btnShareLinkedContentInBack);
        btnGetFriend = (Button) findViewById(R.id.btnGetFriend);
        btnInviteFriend = (Button) findViewById(R.id.btnInviteFriend);
        btnLogout = (Button) findViewById(R.id.btnLogout);


        btnFBLogin.setOnClickListener(this);
        btnGetProfile.setOnClickListener(this);
        btnPostTxtMsg.setOnClickListener(this);
        btnImgMsg.setOnClickListener(this);
        btnImgMsgInback.setOnClickListener(this);
        btnImgAndTxtMsg.setOnClickListener(this);
        btnImgAndTxtMsgInback.setOnClickListener(this);
        btnLinkedContent.setOnClickListener(this);
        btnLinkedContentInback.setOnClickListener(this);
        btnGetFriend.setOnClickListener(this);
        btnInviteFriend.setOnClickListener(this);
        btnLogout.setOnClickListener(this);


        //used for invite friend
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }

        setButtonsState();
    }

    private void setButtonsState() {
        if (TSGFacebookManager.isLogedIn()) {
            llOptionsLayout.setVisibility(View.VISIBLE);
            btnFBLogin.setVisibility(View.GONE);
        } else {
            llOptionsLayout.setVisibility(View.GONE);
            btnFBLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnFBLogin) {
            TSGFacebookManager.requestForLogin(this, new FacebookCallback<LoginResult>() {

                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(FacebookActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();
                    setButtonsState();
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        } else if (v == btnGetProfile) {
            Profile profile = TSGFacebookManager.getProfile();
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        } else if (v == btnPostTxtMsg) {
            TSGFacebookManager.shareTextMessage("Testing..... :)", new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    Toast.makeText(FacebookActivity.this, response.getRawResponse(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnImgMsg) {
            TSGFacebookManager.shareImageMessage(this, bmp, null, facebookShareCallback);
        } else if (v == btnImgMsgInback) {
            TSGFacebookManager.shareImageMessageInBackground(bmp, null, facebookShareCallback);
        } else if (v == btnImgAndTxtMsg) {
            TSGFacebookManager.shareImageMessage(this, bmp, "Yahoooooooooooooooooo...", facebookShareCallback);
        } else if (v == btnImgAndTxtMsgInback) {
            TSGFacebookManager.shareImageMessageInBackground(bmp, "Yahoooooooooooooooooo...", facebookShareCallback);
        } else if (v == btnLinkedContent) {
            TSGFacebookManager.shareLinkedContent(this, "https://developers.facebook.com", "Zipzapzo", "This is the description text", null, facebookShareCallback);
        } else if (v == btnLinkedContentInback) {
            TSGFacebookManager.shareLinkedContentInBackgound("https://developers.facebook.com", "Zipzapzo", "This is the description text", null, facebookShareCallback);
        } else if (v == btnGetFriend) {
            TSGFacebookManager.getFriends(new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    Toast.makeText(FacebookActivity.this, response.getRawResponse(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (v == btnInviteFriend) {
            String appLinkUrl = "https://fb.me/1241148712577097";
            String previewImageUrl = "https://fbexternal-a.akamaihd.net/safe_image.php?d=AQBkqzHumALoUJRx&w=128&h=128&url=https%3A%2F%2Ffbcdn-photos-d-a.akamaihd.net%2Fhphotos-ak-xfa1%2Ft39.2081-0%2F10935987_911967968835908_1597235465_n.png&cfs=1";
            TSGFacebookManager.inviteFriends(this, appLinkUrl, previewImageUrl);
        } else if (v == btnLogout) {
            TSGFacebookManager.logout();
            setButtonsState();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TSGFacebookManager.onActivityResult(requestCode, resultCode, data);
    }


    FacebookCallback<Sharer.Result> facebookShareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = "Error";
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = "Success";
                String id = result.getPostId();
                String alertMessage = String.format("Post ID: %1$s", id);
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(FacebookActivity.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton("OK", null)
                    .show();
        }
    };
}
