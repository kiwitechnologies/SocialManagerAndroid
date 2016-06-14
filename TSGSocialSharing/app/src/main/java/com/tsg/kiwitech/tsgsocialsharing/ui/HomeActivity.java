package com.tsg.kiwitech.tsgsocialsharing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tsg.kiwitech.tsgsocialsharing.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFacebook, btnGoogle, btnLinkedIn, btnTwitter, btnInstagram, btnGoogleCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnGoogle = (Button) findViewById(R.id.btnGoogle);
        btnLinkedIn = (Button) findViewById(R.id.btnLinkedIn);
        btnTwitter = (Button) findViewById(R.id.btnTwitter);
        btnInstagram = (Button) findViewById(R.id.btnInstagram);
        btnGoogleCalendar = (Button) findViewById(R.id.btnGoogleCalendar);


        btnFacebook.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnLinkedIn.setOnClickListener(this);
        btnTwitter.setOnClickListener(this);
        btnInstagram.setOnClickListener(this);
        btnGoogleCalendar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnFacebook) {
            startActivity(new Intent(this, FacebookActivity.class));
        } else if (v == btnTwitter) {
            startActivity(new Intent(this, TwitterActivity.class));
        } else if (v == btnInstagram) {
            startActivity(new Intent(this, InstagramActivity.class));
        } else if (v == btnLinkedIn) {
            startActivity(new Intent(this, LinkedInActivity.class));
        } else if (v == btnGoogle) {
            startActivity(new Intent(this, GoogleActivity.class));
        } else if (v == btnGoogleCalendar) {
            startActivity(new Intent(this, GoogleCalendarActivity.class));
        }
    }
}
