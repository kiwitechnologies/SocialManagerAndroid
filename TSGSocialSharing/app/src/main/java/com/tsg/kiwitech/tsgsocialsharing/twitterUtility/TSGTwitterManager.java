/*
 * Copyright (c) 2016 Kiwitech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsg.kiwitech.tsgsocialsharing.twitterUtility;

import android.content.Context;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.io.File;

import io.fabric.sdk.android.Fabric;

/**
 * Created by kiwitech on 03/06/16.
 */

public class TSGTwitterManager {

    private static final String TWITTER_KEY = "8hol0vX8W2rrmKIVADZEeOhye";
    private static final String TWITTER_SECRET = "jVsF5UP7ADVPt8kJ8jlrUyY53k32NtDGhZougZ0QRAb8KV0JR5";

    private TSGTwitterManager() {
    }

    /**
     * Initialisation
     *
     * @param context
     */
    public static void init(Context context) {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig));
    }

    /**
     * Returns the twitter user profile complete data
     *
     * @param callback
     */
    public static void getProfile(Callback<User> callback) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        twitterApiClient.getAccountService().verifyCredentials(false, false, callback);
    }

    /**
     * It will open the twitter application to share the message if available, otherwise it will open the browser to send the message.
     * This function can also be used without login in twitter. It will open the interface in browser where user can edit the message and send it.
     *
     * @param context
     * @param message
     * @param filePath
     */
    public static void publishPost(Context context, String message, String filePath) {

        TweetComposer.Builder builder = new TweetComposer.Builder(context);
        builder.text(message);
        if (filePath != null) {
            File myImageFile = new File("/path/to/image");
            Uri myImageUri = Uri.fromFile(myImageFile);
            builder.image(myImageUri);
        }
        builder.show();
    }

    /**
     * returns the next user tweets from the provided maxId
     *
     * @param nextSinceId next since id of tweet, to get data with
     * @param callback
     */
    public static void getUserNextTweets(Long nextSinceId, Callback<TimelineResult<Tweet>> callback) {
        long userId = TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId();
        UserTimeline.Builder builder = new UserTimeline.Builder().userId(userId);
        UserTimeline userTimeline = builder.build();
        userTimeline.next(nextSinceId, callback);
    }

    /**
     * returns the previous user tweets from the provided maxId
     *
     * @param maxId    maxId of tweet, to get data with
     * @param callback
     */
    public static void getUserPreviousTweets(Long maxId, Callback<TimelineResult<Tweet>> callback) {
        long userId = TwitterCore.getInstance().getSessionManager().getActiveSession().getUserId();
        UserTimeline.Builder builder = new UserTimeline.Builder().userId(userId);
        UserTimeline userTimeline = builder.build();
        userTimeline.previous(maxId, callback);
    }

    /**
     * returns the next searched tweets from the provided maxId
     *
     * @param query       query for search like #Hello
     * @param nextSinceId next since id of tweet, to get data with
     * @param callback
     */
    public static void getSearchNextTweets(String query, Long nextSinceId, Callback<TimelineResult<Tweet>> callback) {
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(query).build();
        searchTimeline.next(nextSinceId, callback);
    }

    /**
     * returns the previous searched tweets from the provided maxId
     *
     * @param query    query for search like #Hello
     * @param maxId    maxId of tweet, to get data with
     * @param callback
     */
    public static void getSearchPreviousTweets(String query, Long maxId, Callback<TimelineResult<Tweet>> callback) {
        SearchTimeline searchTimeline = new SearchTimeline.Builder().query(query).build();
        searchTimeline.next(maxId, callback);
    }

    /**
     * logout the current twitter user
     *
     * @param context
     */
    public static void logOut(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
    }

    /**
     * Check for the current user is login or not
     *
     * @return
     */
    public static boolean isLogin() {
        return TwitterCore.getInstance().getSessionManager().getActiveSession() != null;
    }

}
