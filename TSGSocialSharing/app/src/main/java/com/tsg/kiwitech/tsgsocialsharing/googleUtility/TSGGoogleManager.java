/*
 * Copyright (c) 2016 Kiwitech
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsg.kiwitech.tsgsocialsharing.googleUtility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;

/**
 * Created by kiwitech on 13/06/16.
 */

public class TSGGoogleManager {

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    private TSGGoogleManager(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener listener) {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(fragmentActivity /* FragmentActivity */, listener /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /**
     * call this method in oncreate method of your activity starting
     *
     * @param context
     * @param fragmentActivity
     * @param listener
     * @return
     */

    public static TSGGoogleManager Init(Context context, FragmentActivity fragmentActivity, GoogleApiClient.OnConnectionFailedListener listener) {
        TSGGoogleManager tsgGoogleManager = new TSGGoogleManager(context, fragmentActivity, listener);
        return tsgGoogleManager;
    }

    /**
     * Signout the current logedin user
     *
     * @param callBack
     */
    public void signOut(ResultCallback callBack) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(callBack);
    }

    /**
     * remove the current logged in user access
     *
     * @param callBack
     */
    public void revokeAccess(ResultCallback callBack) {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(callBack);
    }


    /**
     * returns the scopes array
     *
     * @return
     */
    public Scope[] getScope() {
        return gso.getScopeArray();
    }

    /**
     * call this method for signin user
     *
     * @param activity
     * @param id
     */
    public void signIn(Activity activity, int id) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, id);
    }

}
