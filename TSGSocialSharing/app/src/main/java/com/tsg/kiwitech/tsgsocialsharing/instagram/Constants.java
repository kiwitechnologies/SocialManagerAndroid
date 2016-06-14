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

/**
 * Constants.
 *
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 */
public class Constants {

    public static final String TAG = "AndroidInstagram";

    public static final String AUTH_URL = "https://instagram.com/oauth/authorize/?";
    public static final String GET_SELF_PROFILE = "https://api.instagram.com/v1/users/self";
    public static final String GET_USER_PROFILE = "https://api.instagram.com/v1/users/{user-id}";
    public static final String GET_SELF_RECENT_MEDIA = "https://api.instagram.com/v1/users/self/media/recent";
    public static final String GET_USER_RECENT_MEDIA = "https://api.instagram.com/v1/users/{user-id}/media/recent";
    public static final String GET_FOLLOWS = "https://api.instagram.com/v1/users/self/follows";
    public static final String GET_FOLLOWED_BY = "https://api.instagram.com/v1/users/self/followed-by";


    public final static String REQUEST_TYPE_GET_PROFILE = "getProfile";
    public final static String REQUEST_TYPE_GET_USER_PROFILE = "getUserProfile";
    public final static String REQUEST_TYPE_GET_RECENT_MEDIA = "selfRecentMedia";
    public final static String REQUEST_TYPE_GET_USER_RECENT_MEDIA = "getUserRecentMedia";
    public final static String REQUEST_TYPE_GET_FOLLOWS = "getFollows";
    public final static String REQUEST_TYPE_GET_FOLLOWED_BY = "getFollowedBy";
}