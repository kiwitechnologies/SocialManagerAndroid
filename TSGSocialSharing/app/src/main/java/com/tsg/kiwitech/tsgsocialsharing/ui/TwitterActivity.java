package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.twitterUtility.TSGTwitterManager;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetui.TimelineResult;

public class TwitterActivity extends AppCompatActivity implements View.OnClickListener {

    private TwitterLoginButton loginButton;
    private TSGTwitterManager tsgTwitterManager;
    private Button btnGetProfile, btnPublishPost, btnGetUserPost, btnSearchPost, btnLogout;
    private View optionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TSGTwitterManager.init(this);
        setContentView(R.layout.activity_twitter);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        btnGetProfile = (Button) findViewById(R.id.btnGetProfile);
        btnPublishPost = (Button) findViewById(R.id.btnPublishPost);
        btnGetUserPost = (Button) findViewById(R.id.btnGetUserPost);
        btnSearchPost = (Button) findViewById(R.id.btnGetSearchPost);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        optionsLayout = findViewById(R.id.options_root_layout);

        btnGetProfile.setOnClickListener(this);
        btnPublishPost.setOnClickListener(this);
        btnGetUserPost.setOnClickListener(this);
        btnSearchPost.setOnClickListener(this);
        btnLogout.setOnClickListener(this);


        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "login with Twitter failure", exception);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        resetOptionsLayout();
    }

    private void resetOptionsLayout() {
        if (tsgTwitterManager.isLogin()) {
            optionsLayout.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
        } else {
            optionsLayout.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        if (v == loginButton) {
        } else if (v == btnGetProfile) {
            tsgTwitterManager.getProfile(new Callback<User>() {
                @Override
                public void success(Result<User> userResult) {
                    String name = userResult.data.name;
                    String email = userResult.data.email;

//                    // _normal (48x48px) | _bigger (73x73px) | _mini (24x24px)
//                    String photoUrlNormalSize = userResult.data.profileImageUrl;
//                    String photoUrlBiggerSize = userResult.data.profileImageUrl.replace("_normal", "_bigger");
//                    String photoUrlMiniSize = userResult.data.profileImageUrl.replace("_normal", "_mini");
//                    String photoUrlOriginalSize = userResult.data.profileImageUrl.replace("_normal", "");

                    int status = userResult.response.getStatus();
                    Toast.makeText(TwitterActivity.this, "Response: " + status, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(TwitterException exc) {
                    Log.d("TwitterKit", "Verify Credentials Failure", exc);
                }
            });
        } else if (v == btnPublishPost) {
            tsgTwitterManager.publishPost(this, "Yooo I done this :)", null);
        } else if (v == btnGetUserPost) {
            tsgTwitterManager.getUserNextTweets(null, callback);
        } else if (v == btnSearchPost) {
            tsgTwitterManager.getSearchNextTweets("#hello", null, callback);
        } else if (v == btnLogout) {
            tsgTwitterManager.logOut(this);
            Toast.makeText(this, "Successfully logout", Toast.LENGTH_SHORT).show();
            resetOptionsLayout();
        }

    }

    private Callback<TimelineResult<Tweet>> callback = new Callback<TimelineResult<Tweet>>() {

        @Override
        public void success(Result<TimelineResult<Tweet>> result) {
            Toast.makeText(TwitterActivity.this, result.response.getStatus() + "", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(TwitterActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}
