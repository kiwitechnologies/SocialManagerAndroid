package com.tsg.kiwitech.tsgsocialsharing.linkedInUtility;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.tsg.kiwitech.tsgsocialsharing.ui.LinkedInActivity;

/**
 * Created by kiwitech on 14/06/16.
 */

public class TSGLinkedInManager {

    public static void login(Activity activity, AuthListener authListener) {
        LISessionManager.getInstance(activity.getApplicationContext()).init(activity, buildScope(), authListener, true);
    }

    public static void getProfile(Context context, ApiListener apiListener) {
        APIHelper helper = new APIHelper();
        helper.getRequest(context, Constants.URL_GET_PROFILE, apiListener);
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    public static void getProfileAdditionalInfo(Context context, ApiListener apiListener) {
        APIHelper helper = new APIHelper();
        helper.getRequest(context, Constants.URL_GET_PROFILE_ADDITIONAL_INFO, apiListener);
    }

    public static void getCompanyInfo(Context context, ApiListener apiListener) {
        APIHelper helper = new APIHelper();
        helper.getRequest(context, Constants.URL_GET_COMPANY_INFO, apiListener);
    }

    public static void sharePost(Context context, String comment, ApiListener apiListener) {
        String msg = "{\n" +
                "  \"comment\": \"" + comment + ",\n" +
                "  \"visibility\": {\n" +
                "    \"code\": \"anyone\"\n" +
                "  }\n" +
                "}";
        APIHelper helper = new APIHelper();
        helper.postRequest(context, Constants.URL_SHARE_POST, msg, apiListener);
    }

    public static void shareContentPost(Context context, String comment, String title, String description, String submittedURL, String submittedImageURL, ApiListener apiListener) {
        String msg = "{\n" +
                "  \"comment\": \"" + comment + "\",\n" +
                "  \"content\": {\n" +
                "    \"title\": \"" + title + "\",\n" +
                "    \"description\": \"" + description + "\",\n" +
                "    \"submitted-url\": \"" + submittedURL + "\",\n" +
                "    \"submitted-image-url\": \"" + submittedImageURL + "\"\n" +
                "  },\n" +
                "  \"visibility\": {\n" +
                "    \"code\": \"anyone\"\n" +
                "  }\n" +
                "}";
        APIHelper helper = new APIHelper();
        helper.postRequest(context, Constants.URL_SHARE_POST, msg, apiListener);
    }

    public static void logout(Context context) {
        LISessionManager.getInstance(context).clearSession();
    }
}
