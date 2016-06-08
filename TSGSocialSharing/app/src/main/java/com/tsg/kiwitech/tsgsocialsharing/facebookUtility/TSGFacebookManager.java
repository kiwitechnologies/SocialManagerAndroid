/*
 * Copyright (c) 2016 Kiwitech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.tsg.kiwitech.tsgsocialsharing.facebookUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSGFacebookManager {

    private static List PERMISSIONS_LIST = Arrays.asList("email", "user_photos", "public_profile", "user_posts");
    private static CallbackManager callbackManager;


    private TSGFacebookManager() {
    }

    /**
     * Initialisation
     * @param applicationContext
     */
    public static void init(Context applicationContext) {
        init(applicationContext, PERMISSIONS_LIST);
    }

    /**
     * Initialisation
     * @param applicationContext
     * @param permissionsList
     */
    public static void init(Context applicationContext, List<String> permissionsList) {
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(applicationContext);
        callbackManager = CallbackManager.Factory.create();
        PERMISSIONS_LIST = permissionsList;
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * login the facebook account
     * @param activity
     * @param callback
     */
    public static void requestForLogin(Activity activity, FacebookCallback<LoginResult> callback) {
        LoginManager.getInstance().logInWithReadPermissions(activity, PERMISSIONS_LIST);
        LoginManager.getInstance().registerCallback(callbackManager, callback);
    }

    public static Profile getProfile() {
        return Profile.getCurrentProfile();
    }

    /**
     * this method will share the mesage in background
     * @param message
     * @param callback
     */
    public static void shareTextMessage(String message, GraphRequest.Callback callback) {
        Bundle params = new Bundle();
        params.putString("message", message);
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/feed",
                params,
                HttpMethod.POST, callback)
                .executeAsync();
    }

    /**
     * Share the image with message on facebook user account in background
     * @param activity
     * @param bitmap
     * @param message
     * @param shareCallback
     */
    public static void shareImageMessage(Activity activity, Bitmap bitmap, String message, FacebookCallback<Sharer.Result> shareCallback) {
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.registerCallback(callbackManager, shareCallback);

        SharePhotoContent content = createPhotoContent(bitmap, message);

        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(content);
        } else {
            ShareApi.share(content, shareCallback);
        }
    }

    /**
     * Share the image with message on facebook user account in facebook application if available otherwise it will share it in background
     * @param bitmap
     * @param message
     * @param shareCallback
     */
    public static void shareImageMessageInBackground(Bitmap bitmap, String message, FacebookCallback<Sharer.Result> shareCallback) {
        SharePhotoContent content = createPhotoContent(bitmap, message);
        ShareApi.share(content, shareCallback);
    }

    private static SharePhotoContent createPhotoContent(Bitmap image, String message) {
        SharePhoto.Builder builder = new SharePhoto.Builder();
        builder.setBitmap(image);
        if (message != null && !message.equals("")) {
            builder.setCaption(message);
        }

        SharePhoto sharePhoto = builder.build();

        ArrayList<SharePhoto> photos = new ArrayList<>();
        photos.add(sharePhoto);

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .setPhotos(photos)
                .build();
        return sharePhotoContent;
    }

    /**
     * share the linked content on facebook user account in facebook application if available otherwise it will share it in background
     * @param activity
     * @param contentURL
     * @param contentTitle
     * @param contentDescription
     * @param imageUri
     * @param shareCallback
     */
    public static void shareLinkedContent(Activity activity, String contentURL, String contentTitle, String contentDescription, Uri imageUri, FacebookCallback<Sharer.Result> shareCallback) {
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.registerCallback(callbackManager, shareCallback);

        ShareLinkContent content = createShareLinkedContent(contentURL, contentTitle, contentDescription, imageUri);

        if (ShareDialog.canShow(SharePhotoContent.class)) {
            shareDialog.show(content);
        } else {
            ShareApi.share(content, shareCallback);
        }
    }

    /**
     * share the linked content on facebook user account in background
     * @param contentURL
     * @param contentTitle
     * @param contentDescription
     * @param imageUri
     * @param shareCallback
     */
    public static void shareLinkedContentInBackgound(String contentURL, String contentTitle, String contentDescription, Uri imageUri, FacebookCallback<Sharer.Result> shareCallback) {
        ShareLinkContent content = createShareLinkedContent(contentURL, contentTitle, contentDescription, imageUri);
        ShareApi.share(content, shareCallback);
    }

    private static ShareLinkContent createShareLinkedContent(String contentURL, String contentTitle, String contentDescription, Uri imageUri) {
        ShareLinkContent.Builder builder = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(contentURL))
                .setContentTitle(contentTitle)
                .setContentDescription(contentDescription);

        if (imageUri == null) {
            builder.setImageUrl(imageUri);
        }

        ShareLinkContent content = builder.build();

        return content;
    }

    /**
     * check for the current user has publish permission or not
     * @return
     */
    public static boolean hasPublishPermissionForBackground() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains("publish_actions");
    }

    /**
     * logout the current user
     */
    public static void logout() {
        LoginManager.getInstance().logOut();
    }

    /**
     * Check weather the user is logged in or out
     * @return
     */
    public static boolean isLogedIn() {
        return AccessToken.getCurrentAccessToken() != null;
    }

    /**
     * return the list of friends
     * @param callback
     */
    public static void getFriends(GraphRequest.Callback callback) {
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET, callback)
                .executeAsync();
    }

    /**
     * this function will invite the facebook friends for the application
     * @param activity
     * @param appLinkURL
     * @param previewImageURL
     */
    public static void inviteFriends(Activity activity, String appLinkURL, String previewImageURL) {
        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkURL)
                    .setPreviewImageUrl(previewImageURL)
                    .build();
            AppInviteDialog.show(activity, content);
        }
    }

}
