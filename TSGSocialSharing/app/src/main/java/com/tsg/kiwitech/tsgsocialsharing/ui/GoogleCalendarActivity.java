package com.tsg.kiwitech.tsgsocialsharing.ui;

import com.tsg.kiwitech.tsgsocialsharing.R;
import com.tsg.kiwitech.tsgsocialsharing.googleCalendarUtility.TSGGoogleCalendarManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class GoogleCalendarActivity extends Activity implements EasyPermissions.PermissionCallbacks, TSGGoogleCalendarManager.CallBack {
    //    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button mCallApiButton;


    private TSGGoogleCalendarManager tsgGoogleCalendarManager;


    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_calendar);

        mCallApiButton = (Button) findViewById(R.id.btn);
        mOutputText = (TextView) findViewById(R.id.tvOutput);


        mOutputText.setText("Click the \'Call Google Calendar API\' button to test the API.");
        mCallApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallApiButton.setEnabled(false);
                mOutputText.setText("");
                tsgGoogleCalendarManager.getResultsFromApi();
                mCallApiButton.setEnabled(true);
            }
        });

        tsgGoogleCalendarManager = TSGGoogleCalendarManager.Init(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tsgGoogleCalendarManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    @Override
    public void statusChanged(String status) {
        mOutputText.setText(status);
    }

    @Override
    public void CalendarList(List<String> list) {
        mOutputText.setText(TextUtils.join("\n", list));
    }
}