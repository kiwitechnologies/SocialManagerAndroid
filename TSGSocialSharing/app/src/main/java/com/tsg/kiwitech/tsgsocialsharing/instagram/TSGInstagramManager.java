/*
 * Copyright (c) 2016 Kiwitech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsg.kiwitech.tsgsocialsharing.instagram;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.tsg.kiwitech.tsgsocialsharing.ui.InstagramActivity;

import java.io.File;
import java.util.HashMap;

import static httputility.tsg.com.tsghttpcontroller.HttpUtils.*;

public class TSGInstagramManager {

    private Context mContext;

    private InstagramDialog mDialog;
    private InstagramAuthListener mListener;

    private String mClientId;
    private String mClientSecret;
    private String mRedirectUri;

    /**
     * Instantiate new object of this class.
     *
     * @param context      Context
     * @param clientId     Client id
     * @param clientSecret Client secret
     * @param redirectUri  Redirect uri
     */
    public TSGInstagramManager(Context context, String clientId, String clientSecret, String redirectUri) {
        mContext = context;

        mClientId = clientId;
        mClientSecret = clientSecret;
        mRedirectUri = redirectUri;

        String authUrl = Constants.AUTH_URL + "client_id=" + mClientId + "&redirect_uri=" + mRedirectUri + "&response_type=token&scope=public_content+follower_list";

        mDialog = new InstagramDialog(context, authUrl, redirectUri, new InstagramDialog.InstagramDialogListener() {

            @Override
            public void onSuccess(String accessToken) {
                mListener.onSuccess(accessToken);
            }

            @Override
            public void onError(String error) {
                mListener.onError(error);
            }

            @Override
            public void onCancel() {
                mListener.onCancel();
            }
        });
    }

    /**
     * share the image via intent
     * @param context
     * @param mimeType mime type of image image/*
     * @param mediaPath absolute path of file
     */
    public static void shareImageUsingIntent(Context context, String mimeType, String mediaPath) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(mimeType);
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(share, "Share to"));
    }

    /**
     * share the video via intent
     * @param context
     * @param mimeType mime type of file like "video/*"
     * @param mediaPath absolute path of video
     */
    public static void shareVideoUsingIntent(Context context, String mimeType, String mediaPath){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(mimeType);
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(share, "Share to"));
    }

    /**
     * Authorize user.
     *
     * @param listener Auth listner
     */
    public void authorize(InstagramAuthListener listener) {
        mListener = listener;
        mDialog.show();
    }

    /**
     * Retreive user profile information.
     */
    public void getSelfProfile(String requestType, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_SELF_PROFILE);
        builder.setQueryParameters(map);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * get user profile
     * @param requestType
     * @param userId user Id of instagram user
     * @param access_token
     * @param requestCallBack
     */
    public void getUserProfile(String requestType, String userId, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        HashMap<String, String> urlPathParam = new HashMap<>();
        urlPathParam.put("user-id", userId);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_USER_PROFILE);
        builder.setQueryParameters(map);
        builder.setPathParameters(urlPathParam);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * callback the logged in user recent media
     * @param requestType
     * @param access_token
     * @param requestCallBack
     */
    public void getSelfRecentMedia(String requestType, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_SELF_RECENT_MEDIA);
        builder.setQueryParameters(map);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * callback list of recent media of user(id)
     * @param requestType
     * @param userId
     * @param access_token
     * @param requestCallBack
     */
    public void getUserRecentMedia(String requestType, String userId, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        HashMap<String, String> urlPathParam = new HashMap<>();
        urlPathParam.put("user-id", userId);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_USER_RECENT_MEDIA);
        builder.setQueryParameters(map);
        builder.setPathParameters(urlPathParam);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * callback follows of current user
     * @param requestType
     * @param access_token
     * @param requestCallBack
     */
    public void getFollows(String requestType, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_FOLLOWS);
        builder.setQueryParameters(map);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * callback followedby of current user
     * @param requestType
     * @param access_token
     * @param requestCallBack
     */
    public void getFollowedBy(String requestType, String access_token, RequestCallBack requestCallBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);

        GetRequestBuilder builder = new GetRequestBuilder();
        builder.setSubURL(Constants.GET_FOLLOWED_BY);
        builder.setQueryParameters(map);
        builder.setRequestId(requestType);
        builder.build().enqueRequest(requestCallBack);
    }

    /**
     * logout current user
     * @param context
     */
    public void logout(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    public interface InstagramAuthListener {
        public abstract void onSuccess(String accessToken);

        public abstract void onError(String error);

        public abstract void onCancel();
    }

}