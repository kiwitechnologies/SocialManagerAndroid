Social Sharing-Framework
=============
## Features
1. Facebook ( Login, getProfile, Text sharing, Image sharing, Linked Content Sharing, getFriend, Invite Friend, Logout ).
2. Twitter ( Login, GetProfile, Publish Post, Get user tweets, Get search tweets, Logout).
3. Instagram (Login, getProfile, share image using intent, share video using intent, user profile, self recent media, user recent media, follows, followed by, logout).
4. LinkedIn (Login, Get Profile, Get Profile Additional info, Get company Info, Share Post URL, Share Content, Logout).
5. Google (Login, Disconnect App, Logout).
6. Google Calendar (Login, Get Callendars).

Getting started
----------------
- The SocialSharing  Folder contains TSGSocialSharing project. This example  project contains the sample code for facebook and twitter integration about how to use the social sharing framework and .
- To use the Sharing module in your application, open the Example project and copy the coresponding package of facebook or twitter in your project.
- Configure the related social sharing module in your project.

Strategy configuration
======================

FACEBOOK
--------
1. Create a application on facebook (if not exist), https://developers.facebook.com/quickstarts/?platform=android
2. Go through with below url and set up your application with provided instructions
 https://developers.facebook.com/docs/android/getting-started/
3. Copy "TSGFacebookManger.java" file into your project.
4. Skip the step to initializing the facebook SDK by calling "FacebookSdk.sdkInitialize(getApplicationContext());". Instead of it just call the "TSGFacebookManager.init(getApplicationContext());" function from your activity
5. Override the onActivity result and pass that data to TSGFacebookManager class. eg - 

```sh
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TSGFacebookManager.onActivityResult(requestCode, resultCode, data);
    }
```
6. Starting with TSGFacebookManager.reqeustForLogin() function, you can use any required static function of TSGFacebookManager class.
7. Go throw with the sample code of TSGFacebookManager.java class in FacebookActivity.java .


TWITTER
--------
1. Install Fabric plugin in Android Studio (if not exist).
2. Create new app of twitter using Fabric plugin.
3. Follow the instruction for configure twitter in your project using fabric. - - https://www.youtube.com/watch?v=X9qvO0DIsdk
- http://code.tutsplus.com/tutorials/quick-tip-authentication-with-twitter-and-fabric--cms-23801
4. Copy "TSGTwitterManager" file into your project.
5. Call TSGTwitterManager.init(this); function from your Activity onCreate method, before setting contentView.
6. After setup, you can use any required static function of TSGTwitterManager.java class
7. Go throw with the sample code of TSGTwitterManager.java class in TwitterActivity.java .

INSTAGRAM
--------
1. Sharing of video and image can be done using intent without the need of application credetential but Instagram application is required in Android mobile.
1. Register your application and get ClientId, ClientSecret and Redirect_url at "https://www.instagram.com/developer/".
2. Copy instagram package into your project.
3. Create the instance of TSGInstagramManager class in your activity by providing the ClientId, ClientSecret and RedirectURL as parameter.
4. Use tsgInstagramManager.authorize() function for login.
5. Use tsgInstagramManager instance to call other functions in your application.
6. Gothrow with the sample code of TSGInstagramManager.java class in InstagramActivity.java .

LINKEDIN
--------

1. Register your application at https://www.linkedin.com/developer/apps/new (It not exist)
2. Download and add LinkedIn SDK module in your project and add its dependency.
3. Copy linkedInUtility package in your project
4. Starting with TSGLinkedinManager.login() function, you can use any required static function of TSGFacebookManager class.
5. Go throw with the sample code of TSGLinkedInManager.java class in LinkedInActivity.java .


GOOGLE
------
1. Register your application at https://developers.google.com/mobile/add?platform=android
2. Setup your project by using following instructions https://developers.google.com/identity/sign-in/android/start-integrating
3. Copy googleUtility package in your application.
4. Call the init function of TSGGoogleManager from your activity. eg - TSGGoogleManager.Init() and get the instance fo TSGGoogleManager class;
5. To handle the login request override the onActivityResult function of Activity and get the GoogleSigninResult from there eg- 

```sh
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    }
}
```

from GoogleSigInResult object you will get the google profile data.

6. After login you can call the required function from the TSGGoogleManager class.
7. Go throw with the sample code of TSGGoogleManager.java class in GoogleActivity.java .


GOOGLE CALENDAR
---------------
1. Setup your project by using following instructions https://developers.google.com/google-apps/calendar/quickstart/android.
2. Copy googleCalendarUtility package in your project.
3. Call the init function of TSGGoogleCalendarManager in your activity and get the instance of it. eg- 
```sh
tsgGoogleCalendarManager = TSGGoogleCalendarManager.Init(this);
```
4. Overide the onActivityResult function of Activity and pass the variables in TSGGoogleCalendarManager.OnActivityResult function.
eg- 
```sh
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    tsgGoogleCalendarManager.onActivityResult(requestCode, resultCode, data);
}
 ```
5. Call the getResultsFromAPI function to get the details of Calendar.
6. Go throw with the sample code of TSGGoogleCalendarManager.java class in GoogleCalendarActivity.java .


License
---------
Social Sharing Framework is KiwiTechnolgies Licensed  
Copyright © 2016